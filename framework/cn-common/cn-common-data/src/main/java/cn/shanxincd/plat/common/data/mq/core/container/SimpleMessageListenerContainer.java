package cn.shanxincd.plat.common.data.mq.core.container;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;
import java.util.concurrent.*;

@Slf4j
public class SimpleMessageListenerContainer extends AbstractMessageListenerContainer {
    private final String delayedSuffix = "-delayed";

    private boolean isRunning;

    @Override
    public void doStart() {
        if (isRunning) {
            return;
        }

        isRunning = true;
        for (String queueName : getQueueName()) {
            Executor executor = Executors.newFixedThreadPool(2);
            executor.execute(new AsyncConsumer(queueName));
            executor.execute(new AsyncConsumerDelayed(queueName));
        }
    }

    /** 正常消费队列 */
    private class AsyncConsumer implements Runnable {
        private final String queueName;
        private final String delayedQueueName;

        public AsyncConsumer(String queueName) {
            this.queueName = queueName;
            this.delayedQueueName = queueName + delayedSuffix;
        }

        @Override
        public void run() {
            ExecutorService consumer = new ThreadPoolExecutor(1, getMaxConsumer(), 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
            while (true) {
                try {
                    if (isQuit()) {
                        Thread.sleep(3000);
                        continue;
                    }

                    //从redis中获取，可能存在序列化问题
					final String msg = getRedisTemplate().opsForList().rightPop(queueName);
                    if (msg != null) {
                        final JSONObject json = JSONUtil.parseObj(msg);
                        consumer.execute(() -> {
                            try {
								invoke(json);
                            }
                            catch (Exception e) {
								log.error(String.format("队列=%s消费失败, msg=%s", queueName, msg), e);

								//如果小于最大重试次数 进入死信队列
								if (json.getInt("retryTimes") < getRetryTimes()) {
                                    double score = System.currentTimeMillis() + (3 * 60 * 1000);
									json.set("retryTimes", json.getInt("retryTimes") + 1);
									getRedisTemplate().opsForZSet().add(delayedQueueName, json.toString(), score);
                                }
                            }
                        });
                    }
                    else {
                        Thread.sleep(1000);
                    }
                }
                catch (Exception e) {
                    log.error(e.toString());
                    log.info(String.format("consumer error queues = %s, error = %s", queueName, e.getMessage()));
                }
            }
        }
    }

    /** 错误信息队列 */
    private class AsyncConsumerDelayed implements Runnable {
        private final String queueName;
        private final String delayedQueueName;

        public AsyncConsumerDelayed(String queueName) {
            this.queueName = queueName;
            this.delayedQueueName = queueName + delayedSuffix;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    if (isQuit()) {
                        Thread.sleep(3000);
                        continue;
                    }

                    ZSetOperations<String, String> vo = getRedisTemplate().opsForZSet();
                    Set<ZSetOperations.TypedTuple<String>> items = vo.rangeWithScores(delayedQueueName, 0, 0);

                    assert items != null;
					ZSetOperations.TypedTuple<String> item = items.size() > 0 ? items.iterator().next() : null;
                    if (item == null || item.getScore() > System.currentTimeMillis()) {
                        try {
                            Thread.sleep(100);
                            continue;
                        }
                        catch (Exception e) {
                            log.error(e.toString());
                        }
                    }

                    assert item != null;
                    String msg = item.getValue();

                    Long rSize = vo.remove(delayedQueueName, msg);
                    if (rSize != null && rSize.intValue() > 0) {
						assert msg != null;
						getRedisTemplate().opsForList().leftPush(queueName, msg);
                    }
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
