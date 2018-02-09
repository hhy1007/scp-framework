/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.job.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ip工具类
 * 
 * @author songjie
 * @since 2018年1月17日
 */
public class IPUtils {
	private static final Logger logger = LoggerFactory.getLogger(IPUtils.class);

	/**
	 * ip是否活动
	 * 
	 * @param ips
	 * @return boolean
	 */
	public static boolean isRunable(String ips) {
		String localIp = getIp();
		if (StringUtils.isNotBlank(ips)) {
			for (String configIp : ips.split(",")) {
				if (localIp.equals(configIp)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 多IP处理，可以得到最终ip
	 * 
	 * @return String
	 */
	public static String getIp() {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		String netip = null;// 外网IP
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface
					.getNetworkInterfaces();
			InetAddress ip = null;
			boolean finded = false;// 是否找到外网IP
			while (netInterfaces.hasMoreElements() && !finded) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
					if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 外网IP
						netip = ip.getHostAddress();
						finded = true;
						break;
					} else if (ip.isSiteLocalAddress()
							&& !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
						localip = ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			logger.error(e.getMessage());
		}
		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}

}
