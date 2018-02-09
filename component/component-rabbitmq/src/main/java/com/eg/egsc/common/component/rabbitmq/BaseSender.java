/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.rabbitmq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

/**
 * BaseSender
 * 
 * @author songjie
 * @since 2018年1月17日
 */
public abstract class BaseSender {
	/**
	 * Convert a Java object to an Amqp {@link Message} and send it to a default
	 * exchange with a specific routing key.
	 * 
	 * @param routingKey
	 * @param message
	 * @throws AmqpException
	 *             void
	 */
	public void convertAndSend(String routingKey, Object message)
			throws AmqpException {
		getRabbitTemplate().convertAndSend(routingKey, message);
	}

	/**
	 * Convert a Java object to an Amqp {@link Message} and send it to a
	 * specific exchange with a specific routing key.
	 * 
	 * @param exchange
	 * @param routingKey
	 * @param message
	 * @throws AmqpException
	 *             void
	 */
	public void convertAndSend(String exchange, String routingKey,
			Object message) throws AmqpException {
		getRabbitTemplate().convertAndSend(exchange, routingKey, message);
	}

	/**
	 * Convert a Java object to an Amqp {@link Message} and send it to a
	 * specific exchange with a specific routing key.
	 * 
	 * @param routingKey
	 * @param message
	 * @throws AmqpException
	 *             void
	 */
	public void convertAndSendTopic(String routingKey, Object message)
			throws AmqpException {
		getRabbitTemplate().convertAndSend(Properties.TOPIC_EXCHANGE,
				routingKey, message);
	}

	/**
	 * 获取 AmqpTemplate
	 * 
	 * @return AmqpTemplate
	 */
	public abstract AmqpTemplate getRabbitTemplate();

	/**
	 * 获取 ConnectionFactory
	 * 
	 * @return ConnectionFactory
	 */
	public abstract ConnectionFactory getConnectionFactory();
}
