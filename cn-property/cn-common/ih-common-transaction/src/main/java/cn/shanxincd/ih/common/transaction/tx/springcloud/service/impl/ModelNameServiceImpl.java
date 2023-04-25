package cn.shanxincd.ih.common.transaction.tx.springcloud.service.impl;

import cn.shanxincd.ih.common.transaction.tx.springcloud.listener.ServerListener;
import com.codingapi.tx.listener.service.ModelNameService;
import com.lorne.core.framework.utils.encode.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
@Configuration
public class ModelNameServiceImpl implements ModelNameService {

	@Value("${spring.application.name}")
	private String modelName;

	@Autowired
	private ServerListener serverListener;

	private String host = null;

	@Override
	public String getModelName() {
		return modelName;
	}

	private String getIp() {
		if (host == null) {
			try {
				host = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		return host;
	}

	private int getPort() {
		int port = serverListener.getPort();
		int count = 0;
		while (port == 0) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			port = serverListener.getPort();
			count++;

			if (count == 2000) {
				throw new RuntimeException("get server port error.");
			}
		}

		return port;
	}

	@Override
	public String getUniqueKey() {
		String address = getIp() + getPort();
		return MD5Util.md5(address.getBytes());
	}

	@Override
	public String getIpAddress() {
		return getIp() + ":" + getPort();
	}
}
