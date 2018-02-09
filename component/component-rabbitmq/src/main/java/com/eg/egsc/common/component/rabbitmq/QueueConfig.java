/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 定义queue 继承QueueConfig类，通过调用QueueConfig中的defineQueue方法定义queue
 * 
 * @author songjie
 * @since 2018年1月17日
 */
@Component
public class QueueConfig {
  
  public static final String LOG_DES = "定义queue失败[";
  public static final String LOG_DESC = "],异常：";
	@Resource
	private CachingConnectionFactory connectionFactory;

	@Resource(name = "iotbusConnectionFactory")
	private CachingConnectionFactory iotbusConnectionFactory;

	@Resource(name = "logserviceConnectionFactory")
	private CachingConnectionFactory logserviceConnectionFactory;

	@Autowired
	private Properties rabbitProperties;

	@Autowired
	private TopicExchange topicExchange;

	@Autowired
	private TopicExchange iotbusTopicExchange;

	private static final Logger logger = LoggerFactory
			.getLogger(QueueConfig.class);

	/**
	 * 初始化定义queue
	 * 
	 * @return QueueConfig
	 */
	@Bean
	public QueueConfig initQueue() {
		logger.info("系统加载定义queue......");
		// queue定义
		String queues = rabbitProperties.getQueues();
		String topics = rabbitProperties.getTopics();
		try {
			if (rabbitProperties.validQueue()) {
				// queue定义
				queueDeclare(connectionFactory, queues);
				// topic定义
				queueDeclare(connectionFactory, topics);
			}
		} catch (IOException | TimeoutException e) {
			logger.error(LOG_DES + queues + LOG_DESC + e.getMessage());
		}

		// iotbus queue定义
		String iotbusQueues = rabbitProperties.getIotbusQueues();
		String iotbusTopics = rabbitProperties.getIotbusTopics();
		try {
			if (rabbitProperties.validIotbusQueue()) {
				queueDeclare(iotbusConnectionFactory, iotbusQueues);
				queueDeclare(iotbusConnectionFactory, iotbusTopics);
			}
		} catch (IOException | TimeoutException e) {
			logger.error(LOG_DES + queues + LOG_DESC + e.getMessage());
		}

		// 日志服务queue定义
		String logserviceQueues = rabbitProperties.getLogQueues();
		try {
			if (rabbitProperties.validLogQueue()) {
				// queue定义
				queueDeclare(logserviceConnectionFactory, logserviceQueues);
			}
		} catch (IOException | TimeoutException e) {
			logger.error(LOG_DES + queues + LOG_DESC + e.getMessage());
		}

		// 绑定topic
		String routing = rabbitProperties.getRouting();
		if (!isEmpty(routing)) {
			try {
				bingExchange(connectionFactory, topicExchange.getName(),
						routing);
			} catch (IOException | TimeoutException e) {
				logger.error(LOG_DES + queues + LOG_DESC + e.getMessage());
			}
		}

		String iotbusRouting = rabbitProperties.getIotbusRouting();
		if (!isEmpty(iotbusRouting)) {
			try {
				bingExchange(iotbusConnectionFactory,
						iotbusTopicExchange.getName(), iotbusRouting);
			} catch (IOException | TimeoutException e) {
				logger.error(LOG_DES + queues + LOG_DESC + e.getMessage());
			}

		}
		return new QueueConfig();
	}

	/**
	 * 定义queue
	 * 
	 * @param factory
	 * @param queues
	 * @throws IOException
	 * @throws TimeoutException
	 *             void
	 */
	public void queueDeclare(CachingConnectionFactory factory, String queues)
			throws IOException, TimeoutException {
		if (isEmpty(queues)) {
			return;
		}
		String[] queueArr = queues.split(",");
		if (null != queueArr && queueArr.length > 0) {
			Connection connection = factory.getRabbitConnectionFactory()
					.newConnection();
			Channel channel = connection.createChannel();
			channel.basicQos(1, false);
			for (String queueName : queueArr) {
				if (!isEmpty(queueName)) {
					channel.queueDeclare(queueName, true, false, false, null);
					logger.info("queueDeclare成功:" + queueName);
				}
			}
		}
	}

	/**
	 * 绑定路由器
	 * 
	 * @param factory
	 * @param exchange
	 * @param routing
	 * @throws IOException
	 * @throws TimeoutException
	 *             void
	 */
	public void bingExchange(CachingConnectionFactory factory, String exchange,
			String routing) throws IOException, TimeoutException {
		// String routing
		if (isEmpty(routing) || isEmpty(exchange)) {
			logger.info("bingExchange失败：参数不能为空");
			return;
		}
		String[] routingArr = routing.trim().split("&");
		if (null == routingArr || routingArr.length == 0) {
			return;
		}
		Connection connection = factory.getRabbitConnectionFactory()
				.newConnection();
		Channel channel = connection.createChannel();
		channel.basicQos(1, false);
		for (String routingName : routingArr) {
			if (isEmpty(routingName)) {
				continue;
			}
			logger.info("定义routingName:" + routingName);
			String[] routingObj = routingName.trim().split(":");
			if (null == routingObj || routingObj.length <= 1) {
				continue;
			}
			String routingKey = routingObj[0];
			String routingValues = routingObj[1];
			if (isEmpty(routingKey) || isEmpty(routingValues)) {
				logger.info("routing 格式不正确，routing=" + routingName
						+ "");
				continue;
			}
			logger.info("routingKey = " + routingKey
					+ " & routings = " + routingValues + "");
			String[] routingValueArr = routingValues.split(",");
			if (null == routingValueArr || routingValueArr.length == 0) {
				continue;
			}
			for (String queue : routingValueArr) {
				if (isEmpty(queue)) {
					logger.info("routingKey = " + routingKey
							+ " & routingValue = " + queue + "");
					continue;
				}
				channel.queueBind(queue, exchange, routingKey);
				logger.info("#####################      绑定topic成功: topicName=topic.exchange & queue="
						+ queue + " & routingKey=" + routingKey);
			}
		}
	}

	/**
	 * 是否为空自字符串
	 * 
	 * @param str
	 * @return boolean
	 */
	public boolean isEmpty(String str) {
		return StringUtils.isEmpty(str) || StringUtils.isEmpty(str.trim());
	}
}
