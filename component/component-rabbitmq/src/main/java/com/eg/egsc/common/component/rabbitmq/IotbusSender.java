/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * iotbus生产消息类
 * 
 * @author songjie
 * @since 2018年1月17日
 */
public class IotbusSender extends BaseSender {
	@Autowired
	private AmqpTemplate iotbusRabbitTemplate;

	@Autowired
	private ConnectionFactory iotbusConnectionFactory;

	/**
	 * 获取 AmqpTemplate
	 * 
	 * @return AmqpTemplate
	 */
	@Override
	public AmqpTemplate getRabbitTemplate() {
		return iotbusRabbitTemplate;
	}

	/**
	 * 获取 ConnectionFactory
	 * 
	 * @return ConnectionFactory
	 */
	@Override
	public ConnectionFactory getConnectionFactory() {
		return iotbusConnectionFactory;
	}

}
