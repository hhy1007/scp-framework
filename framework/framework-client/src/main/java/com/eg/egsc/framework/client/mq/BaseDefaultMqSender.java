/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.client.mq;

import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;

import com.eg.egsc.common.component.rabbitmq.DefaultSender;
import com.eg.egsc.common.exception.CommonException;
import com.eg.egsc.framework.client.dto.BaseBusinessDto;

/**
 * 
 * 生产消息类
 * 
 * @author songjie
 *
 */
public class BaseDefaultMqSender extends DefaultSender {
	private static final String INFO_NOT_SUPPORT = "this methord is not support!";
	
	@Autowired
    private LogserviceMqSender logserviceMqSender;
	
	/**
	 * 发送queue
	 * @param routingKey
	 * @param baseBusinessDto
	 */
	public void sendMessage(String routingKey,
			BaseBusinessDto baseBusinessDto) {
		logserviceMqSender.log(routingKey, baseBusinessDto, "1");
		super.convertAndSend(routingKey, baseBusinessDto);
	}
	
	/**
	 * 发送topic
	 * @param routingKey
	 * @param baseBusinessDto
	 */
	public void sendTopic(String routingKey,
			BaseBusinessDto baseBusinessDto) {
		logserviceMqSender.log(routingKey, baseBusinessDto, "1");
		super.convertAndSendTopic(routingKey, baseBusinessDto);
	}

	/**
	 * @param routingKey
	 * @param message
	 * @return
	 */
	@Override
	public void convertAndSend(String routingKey, Object message) throws AmqpException{
		throw new CommonException(INFO_NOT_SUPPORT);
	}
	
	/**
	 * @param exchange
	 * @param routingKey
	 * @param message
	 * @return
	 */
	@Override
	public void convertAndSend(String exchange, String routingKey, Object message) throws AmqpException{
		throw new CommonException(INFO_NOT_SUPPORT);
	}
    
	/**
	 * @param routingKey
	 * @param message
	 */
	@Override
    public void convertAndSendTopic(String routingKey, Object message) throws AmqpException{
		throw new CommonException(INFO_NOT_SUPPORT);
	}
}
