package cn.shanxincd.plat.common.core.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.system.SystemUtil;

import java.net.Inet4Address;

/**
 * @author zhangchunlei
 * @date 2022年12月06日 5:16 下午
 */
public class IdUtils {


	public static String getSnowflakeId(){
		return IdUtil.getSnowflake(getWorkId(),getDataCenterId()).nextIdStr();
	}

	/**
	 * workId使用IP生成
	 * @return workId
	 */
	private static Long getWorkId() {
		try {
			String hostAddress = Inet4Address.getLocalHost().getHostAddress();
			int[] ints = hostAddress.codePoints().toArray();
			int sums = 0;
			for (int b : ints) {
				sums = sums + b;
			}
			return (long) (sums % 32);
		}
		catch (Exception e) {
			// 失败就随机
			return RandomUtil.randomLong(0, 31);
		}
	}


	private static Long getDataCenterId() {
		try {
			String hostName = SystemUtil.getHostInfo().getName();
			int[] ints = hostName.codePoints().toArray();
			int sums = 0;
			for (int i: ints) {
				sums = sums + i;
			}
			return (long) (sums % 32);
		}
		catch (Exception e) {
			// 失败就随机
			return RandomUtil.randomLong(0, 31);
		}
	}

}
