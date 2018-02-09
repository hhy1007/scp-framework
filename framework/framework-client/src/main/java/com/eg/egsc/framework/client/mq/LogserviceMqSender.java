/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.client.mq;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eg.egsc.common.component.rabbitmq.LogserviceSender;
import com.eg.egsc.common.component.rabbitmq.Properties;
import com.eg.egsc.common.exception.CommonException;
import com.eg.egsc.framework.client.dto.BaseBusinessDto;
import com.eg.egsc.framework.logservice.dto.LogDto;

/**
 * 
 * 日志服务 生产消息类
 * 
 * @author songjie
 *
 */
@Component
public class LogserviceMqSender extends LogserviceSender {
	private static final String INFO_NOT_SUPPORT = "this methord is not support!";
	
	@Autowired
	private Properties rabbitProperties;

	private static final Logger logger = LoggerFactory
			.getLogger(LogserviceMqSender.class);
	private static final String RABBITMQ_LOG = "rabbitmqlog";

	/**
	 * 
	 * @param routingKey
	 * @param baseBusinessDto
	 * @param operation 1发送,2接收
	 * @return
	 */
	public void log(String routingKey, BaseBusinessDto baseBusinessDto, String operation) {
		//写日志，发送日志消息到mq
		String queue = rabbitProperties.getLogQueues();// 日志服务Q
		if (StringUtils.isEmpty(queue)) {
			logger.info("缺少日志服务queue名称配置信息");
			return;
		}
		LogDto logDto = getlog(baseBusinessDto);
		logDto.setOperation(operation);
		logDto.setOperateTime(new Date());
		logDto.setIntegrate("1");//继承方式，1异步，2同步
		logDto.setUrl(super.getConnectionFactory().getHost() + ":"
				+ super.getConnectionFactory().getPort() + "/" + routingKey);
		try {
			super.convertAndSend(queue, logDto);
		} catch (Exception e) {
			logger.info("发送日志消息失败，异常：" + e.getMessage());
		}
	}

	public LogDto getlog(BaseBusinessDto baseBusinessDto) {
		//写日志，发送日志消息到mq
		LogDto logDto = new LogDto();
		if (null != baseBusinessDto) {
			logDto.setBusinessId(baseBusinessDto.getBusinessId());
			logDto.setSourceSysId(baseBusinessDto.getSourceSysId());
			logDto.setExtAttributes(baseBusinessDto.getExtAttributes());
			logDto.setTargetSysId(baseBusinessDto.getTargetSysId());
			logDto.setRequestMessage(baseBusinessDto.toString());
			logDto.setType(RABBITMQ_LOG);
		}
		return logDto;
	}

	/**
	 * @param routingKey
	 * @param message
	 */
	@Override
	public void convertAndSend(String routingKey, Object message)
			throws AmqpException {
		throw new CommonException(INFO_NOT_SUPPORT);
	}

	/**
	 * @param exchange
	 * @param routingKey
	 * @param message
	 */
	@Override
	public void convertAndSend(String exchange, String routingKey,
			Object message) throws AmqpException {
		throw new CommonException(INFO_NOT_SUPPORT);
	}

	/**
	 * @param routingKey
	 * @param message
	 */
	@Override
	public void convertAndSendTopic(String routingKey, Object message)
			throws AmqpException {
		throw new CommonException(INFO_NOT_SUPPORT);
	}
}
