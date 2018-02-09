/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.rabbitmq;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class Properties {
	private String host;
	private String port;
	private String username;
	private String password;
	private String virtualHost;
	private String queues;
	private String topics;
	private String routing;
	private String exchange;

	private String iotbusHost;
	private String iotbusPort;
	private String iotbusUsername;
	private String iotbusPassword;
	private String iotbusVirtualHost;
	private String iotbusQueues;
	private String iotbusTopics;
	private String iotbusRouting;
	private String iotbusExchange;

	private String logHost;
	private String logPort;
	private String logUsername;
	private String logPassword;
	private String logVirtualHost;
	private String logQueues;

	@Autowired
	private Environment env;

	public static final String TOPIC_EXCHANGE = "TOPIC.EXCHANGE";

	/**
	 * Properties
	 * 
	 * @return Properties
	 */
	@Bean(name = "rabbitProperties")
	public Properties properties() {
		Properties properties = new Properties();
		// 默认mq
		host = env.getProperty("spring.rabbitmq.host", "");
		port = env.getProperty("spring.rabbitmq.port", "");
		username = env.getProperty("spring.rabbitmq.username", "");
		password = env.getProperty("spring.rabbitmq.password", "");
		virtualHost = env.getProperty("spring.rabbitmq.virtualHost", "");
		queues = env.getProperty("spring.rabbitmq.queues", "");
		topics = env.getProperty("spring.rabbitmq.topics", "");
		routing = env.getProperty("spring.rabbitmq.topic.routing", "");
		exchange = env.getProperty("spring.rabbitmq.topic.exchange", "false");

		// iotbus mq
		iotbusHost = env.getProperty("iotbus.rabbitmq.host", "");
		iotbusPort = env.getProperty("iotbus.rabbitmq.port", "");
		iotbusUsername = env.getProperty("iotbus.rabbitmq.username", "");
		iotbusPassword = env.getProperty("iotbus.rabbitmq.password", "");
		iotbusVirtualHost = env.getProperty("iotbus.rabbitmq.virtualHost", "");
		iotbusQueues = env.getProperty("iotbus.rabbitmq.queues", "");
		iotbusTopics = env.getProperty("iotbus.rabbitmq.topics", "");
		iotbusRouting = env.getProperty("iotbus.rabbitmq.topic.routing", "");
		iotbusExchange = env.getProperty("iotbus.rabbitmq.topic.exchange",
				"false");

		// logservice mq
		logHost = env.getProperty("log.rabbitmq.host", "");
		logPort = env.getProperty("log.rabbitmq.port", "");
		logUsername = env.getProperty("log.rabbitmq.username", "");
		logPassword = env.getProperty("log.rabbitmq.password", "");
		logVirtualHost = env.getProperty("log.rabbitmq.virtualHost", "");
		logQueues = env.getProperty("log.rabbitmq.queues", "");

		properties.setHost(host);
		properties.setPort(port);
		properties.setUsername(username);
		properties.setPassword(password);
		properties.setVirtualHost(virtualHost);
		properties.setQueues(queues);
		properties.setTopics(topics);
		properties.setRouting(routing);
		properties.setExchange(exchange);

		properties.setIotbusHost(iotbusHost);
		properties.setIotbusPort(iotbusPort);
		properties.setIotbusUsername(iotbusUsername);
		properties.setIotbusPassword(iotbusPassword);
		properties.setIotVirtualHost(iotbusVirtualHost);
		properties.setIotbusQueues(iotbusQueues);
		properties.setIotbusTopics(iotbusTopics);
		properties.setIotbusRouting(iotbusRouting);
		properties.setIotbusExchange(iotbusExchange);

		properties.setLogHost(logHost);
		properties.setLogPort(logPort);
		properties.setLogUsername(logUsername);
		properties.setLogPassword(logPassword);
		properties.setLogVirtualHost(logVirtualHost);
		properties.setLogQueues(logQueues);
		return properties;
	}

	/**
	 * 是否配置默认mq
	 * 
	 * @return boolean
	 */
	public boolean validQueue() {
		if (StringUtils.isEmpty(getHost()) || StringUtils.isEmpty(getPort())
				|| !isNumeric(getPort()) || StringUtils.isEmpty(getUsername())
				|| StringUtils.isEmpty(getPassword())) {
			return false;
		}
		return true;
	}

	/**
	 * 是否配置iotbusmq
	 * 
	 * @return boolean
	 */
	public boolean validIotbusQueue() {
		if (StringUtils.isEmpty(getIotbusHost())
				|| StringUtils.isEmpty(getIotbusPort())
				|| !isNumeric(getIotbusPort())
				|| StringUtils.isEmpty(getIotbusUsername())
				|| StringUtils.isEmpty(getIotbusPassword())) {
			return false;
		}
		return true;
	}

	/**
	 * 是否配置日志服务mq
	 * 
	 * @return boolean
	 */
	public boolean validLogQueue() {
		if (StringUtils.isEmpty(getLogHost())
				|| StringUtils.isEmpty(getLogPort())
				|| !isNumeric(getLogPort())
				|| StringUtils.isEmpty(getLogUsername())
				|| StringUtils.isEmpty(getLogPassword())) {
			return false;
		}
		return true;
	}

	/**
	 * 正则表达式判断字符串是否是数字
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the virtualHost
	 */
	public String getVirtualHost() {
		return virtualHost;
	}

	/**
	 * @param virtualHost
	 *            the virtualHost to set
	 */
	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	/**
	 * @return the queues
	 */
	public String getQueues() {
		return queues;
	}

	/**
	 * @param queues
	 *            the queues to set
	 */
	public void setQueues(String queues) {
		this.queues = queues;
	}

	/**
	 * @return the topics
	 */
	public String getTopics() {
		return topics;
	}

	/**
	 * @param topics
	 *            the topics to set
	 */
	public void setTopics(String topics) {
		this.topics = topics;
	}

	/**
	 * @return the routing
	 */
	public String getRouting() {
		return routing;
	}

	/**
	 * @param routing
	 *            the routing to set
	 */
	public void setRouting(String routing) {
		this.routing = routing;
	}

	/**
	 * @return the exchange
	 */
	public String getExchange() {
		return exchange;
	}

	/**
	 * @param exchange
	 *            the exchange to set
	 */
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	/**
	 * @return the iotbusHost
	 */
	public String getIotbusHost() {
		return iotbusHost;
	}

	/**
	 * @param iotbusHost
	 *            the iotbusHost to set
	 */
	public void setIotbusHost(String iotbusHost) {
		this.iotbusHost = iotbusHost;
	}

	/**
	 * @return the iotbusPort
	 */
	public String getIotbusPort() {
		return iotbusPort;
	}

	/**
	 * @param iotbusPort
	 *            the iotbusPort to set
	 */
	public void setIotbusPort(String iotbusPort) {
		this.iotbusPort = iotbusPort;
	}

	/**
	 * @return the iotbusUsername
	 */
	public String getIotbusUsername() {
		return iotbusUsername;
	}

	/**
	 * @param iotbusUsername
	 *            the iotbusUsername to set
	 */
	public void setIotbusUsername(String iotbusUsername) {
		this.iotbusUsername = iotbusUsername;
	}

	/**
	 * @return the iotbusPassword
	 */
	public String getIotbusPassword() {
		return iotbusPassword;
	}

	/**
	 * @param iotbusPassword
	 *            the iotbusPassword to set
	 */
	public void setIotbusPassword(String iotbusPassword) {
		this.iotbusPassword = iotbusPassword;
	}

	/**
	 * @return the iotVirtualHost
	 */
	public String getIotVirtualHost() {
		return iotbusVirtualHost;
	}

	/**
	 * @param iotVirtualHost
	 *            the iotVirtualHost to set
	 */
	public void setIotVirtualHost(String iotVirtualHost) {
		this.iotbusVirtualHost = iotVirtualHost;
	}

	/**
	 * @return the iotbusQueues
	 */
	public String getIotbusQueues() {
		return iotbusQueues;
	}

	/**
	 * @param iotbusQueues
	 *            the iotbusQueues to set
	 */
	public void setIotbusQueues(String iotbusQueues) {
		this.iotbusQueues = iotbusQueues;
	}

	/**
	 * @return the iotbusTopics
	 */
	public String getIotbusTopics() {
		return iotbusTopics;
	}

	/**
	 * @param iotbusTopics
	 *            the iotbusTopics to set
	 */
	public void setIotbusTopics(String iotbusTopics) {
		this.iotbusTopics = iotbusTopics;
	}

	/**
	 * @return the iotbusRouting
	 */
	public String getIotbusRouting() {
		return iotbusRouting;
	}

	/**
	 * @param iotbusRouting
	 *            the iotbusRouting to set
	 */
	public void setIotbusRouting(String iotbusRouting) {
		this.iotbusRouting = iotbusRouting;
	}

	/**
	 * @return the iotbusExchange
	 */
	public String getIotbusExchange() {
		return iotbusExchange;
	}

	/**
	 * @param iotbusExchange
	 *            the iotbusExchange to set
	 */
	public void setIotbusExchange(String iotbusExchange) {
		this.iotbusExchange = iotbusExchange;
	}

	/**
	 * @return the logHost
	 */
	public String getLogHost() {
		return logHost;
	}

	/**
	 * @param logHost
	 *            the logHost to set
	 */
	public void setLogHost(String logHost) {
		this.logHost = logHost;
	}

	/**
	 * @return the logPort
	 */
	public String getLogPort() {
		return logPort;
	}

	/**
	 * @param logPort
	 *            the logPort to set
	 */
	public void setLogPort(String logPort) {
		this.logPort = logPort;
	}

	/**
	 * @return the logUsername
	 */
	public String getLogUsername() {
		return logUsername;
	}

	/**
	 * @param logUsername
	 *            the logUsername to set
	 */
	public void setLogUsername(String logUsername) {
		this.logUsername = logUsername;
	}

	/**
	 * @return the logPassword
	 */
	public String getLogPassword() {
		return logPassword;
	}

	/**
	 * @param logPassword
	 *            the logPassword to set
	 */
	public void setLogPassword(String logPassword) {
		this.logPassword = logPassword;
	}

	/**
	 * @return the logVirtualHost
	 */
	public String getLogVirtualHost() {
		return logVirtualHost;
	}

	/**
	 * @param logVirtualHost
	 *            the logVirtualHost to set
	 */
	public void setLogVirtualHost(String logVirtualHost) {
		this.logVirtualHost = logVirtualHost;
	}

	/**
	 * @return the logQueues
	 */
	public String getLogQueues() {
		return logQueues;
	}

	/**
	 * @param logQueues
	 *            the logQueues to set
	 */
	public void setLogQueues(String logQueues) {
		this.logQueues = logQueues;
	}

}
