/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * JVM Time Utility
 * 
 * @author douguoqiang
 * @since 2018年1月15日
 */
public class JVMTimeUtils {
	private JVMTimeUtils(){
		throw new IllegalStateException("Utility class");
	}
	
	public static long getJVMStartTime() {
		RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
		return runtimeBean.getStartTime();
	}

	public static long getJVMUpTime() {
		RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
		return runtimeBean.getUptime();
	}
}
