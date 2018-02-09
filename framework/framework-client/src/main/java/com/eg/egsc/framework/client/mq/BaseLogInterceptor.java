/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.client.mq;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eg.egsc.framework.client.dto.BaseBusinessDto;

/** 
 */
@Aspect
@Component
public class BaseLogInterceptor {
	@Autowired
	private LogserviceMqSender logserviceMqSender;

	private static final Logger logger = LoggerFactory
			.getLogger(BaseLogInterceptor.class);

	/**
	 * 定义拦截规则：拦截所有类中，有@RabbitHandler注解的方法。
	 */
	@Pointcut("@annotation(org.springframework.amqp.rabbit.annotation.RabbitHandler)")
	public void baseLogPointcut() {
		// Do nothing
	}

	/**
	 * 拦截器具体实现
	 * 
	 * @param pjp
	 * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
	 */
	@Around("baseLogPointcut()")
	public void interceptor(ProceedingJoinPoint pjp) {
		if (null != pjp) {
			logger.debug(" ------- aop logservice ------- ");
			Object[] args = pjp.getArgs();
			for (Object arg : args) {
				if (arg instanceof BaseBusinessDto) {
					BaseBusinessDto dto = (BaseBusinessDto) arg;
					logger.debug(dto.getBusinessId());
					logserviceMqSender.log("receive", dto, "2");
				}
			}
			try {
				// 一切正常的情况下，继续执行被拦截的方法
				pjp.proceed();
			} catch (Throwable e) {
				logger.info("exception: " + e);
			}
		}
	}
}
