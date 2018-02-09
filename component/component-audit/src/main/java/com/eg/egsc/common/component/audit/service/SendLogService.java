/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.audit.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.eg.egsc.common.component.rabbitmq.DefaultSender;

/**
 * 
 * Sender Log Service
 * 
 * @author guofeng
 * @since 2018年1月15日
 */
@Component
@ComponentScan(basePackages = {"com.eg.egsc"})
public class SendLogService extends DefaultSender {
  private static String Q_DEMO = "EGSC_SCP_LOGSERVICE_LOG_QUEUE";

  public void sendMsg(Object sendObj) {
    System.out.println(Q_DEMO + " Sender string : " + sendObj.toString());
    convertAndSend(Q_DEMO, sendObj);

  }
}
