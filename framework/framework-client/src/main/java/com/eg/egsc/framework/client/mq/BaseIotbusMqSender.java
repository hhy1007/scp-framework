/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.client.mq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;

import com.eg.egsc.common.component.rabbitmq.IotbusSender;
import com.eg.egsc.common.exception.CommonException;
import com.eg.egsc.framework.client.dto.BaseBusinessDto;

/**
 * 
 * iotbus 生产消息类
 * 
 * @author songjie
 *
 */
public class BaseIotbusMqSender extends IotbusSender {
	/**
	 * 发送queue
	 * 
	 * @param routingKey
	 * @param baseBusinessDto
	 */
	public void sendMessage(String routingKey, BaseBusinessDto baseBusinessDto) {
		super.convertAndSend(routingKey, baseBusinessDto);
	}

	/**
	 * 发送topic
	 * 
	 * @param routingKey
	 * @param baseBusinessDto
	 */
	public void sendTopic(String routingKey, BaseBusinessDto baseBusinessDto) {
		super.convertAndSendTopic(routingKey, baseBusinessDto);
	}

	/**
	 * 发送queue
	 * 
	 * @param routingKey
	 * @param message
	 */
	public void sendMessage(String routingKey, Message message) {
		super.convertAndSend(routingKey, message);
	}

	/**
	 * 发送topic
	 * 
	 * @param routingKey
	 * @param message
	 */
	public void sendTopic(String routingKey, Message message) {
		super.convertAndSendTopic(routingKey, message);
	}
	
	/**
	 * 发送queue
	 * @param exchange
	 * @param routingKey
	 * @param message
	 */
	public void sendMessage(String exchange, String routingKey, Message message) {
		super.convertAndSend(routingKey, message);
	}

	/**
	 * 发送topic
	 * @param exchange
	 * @param routingKey
	 * @param message
	 */
	public void sendTopic(String exchange, String routingKey, Message message) {
		super.convertAndSendTopic(routingKey, message);
	}
	
	/**
	 * @param routingKey
	 * @param message
	 */
	@Override
	public void convertAndSend(String routingKey, Object message)
			throws AmqpException {
		throw new CommonException("this method is not support!");
	}

	/**
	 * @param exchange
	 * @param routingKey
	 * @param message
	 */
	@Override
	public void convertAndSend(String exchange, String routingKey,
			Object message) throws AmqpException {
		throw new CommonException("this method is not support !");
	}

	/**
	 * @param routingKey
	 * @param message
	 */
	@Override
	public void convertAndSendTopic(String routingKey, Object message)
			throws AmqpException {
		throw new CommonException("this method is not support!");
	}
}
