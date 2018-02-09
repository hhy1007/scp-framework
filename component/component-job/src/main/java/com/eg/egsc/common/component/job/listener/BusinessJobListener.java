/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.job.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.eg.egsc.common.component.job.support.JobMonitor;

/**
 * job监听类
 * 
 * @author songjie
 * @since 2018年1月17日
 */
@WebListener
public class BusinessJobListener implements ServletContextListener {

	@Autowired
	private JobMonitor monitor;

	/**
	 * 启动监控器
	 * 
	 * @param sce
	 *            void
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		if (monitor != null && !monitor.isActive()) {
			monitor.start();
		}
	}

	/**
	 * 地址监控器
	 * 
	 * @param sce
	 *            void
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if (monitor != null && monitor.isActive()) {
			monitor.stop();
		}
	}
}
