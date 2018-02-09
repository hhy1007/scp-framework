/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.audit.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Audit log interface.
 * 
 * @author guofeng
 * @since 2018年1月15日
 */
public interface AuditLogService {


  /**
   * 截取请求报文，并发送到MQ
   * 
   * @param request void
   */
  void preRoutingFilter(HttpServletRequest request);

  /**
   * 截取返回报文，并发送到MQ
   * 
   * @param request
   * @param responseData
   * @return String
   */
  String postRoutingFilter(HttpServletRequest request, HttpServletResponse response,
      String responseData, boolean isDownload);

}
