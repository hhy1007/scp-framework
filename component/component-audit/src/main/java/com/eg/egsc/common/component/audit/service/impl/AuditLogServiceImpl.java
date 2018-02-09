/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.audit.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.eg.egsc.common.component.audit.dto.AuditLogDto;
import com.eg.egsc.common.component.audit.service.AuditLogService;
import com.eg.egsc.common.component.audit.service.SendLogService;
import com.eg.egsc.common.component.audit.util.AuditUtil;

/**
 * Audit log service implement
 * 
 * @author guofeng
 * @since 2018年1月15日
 */
@Service
public class AuditLogServiceImpl implements AuditLogService {

  protected final Logger logger = LoggerFactory.getLogger(AuditLogServiceImpl.class);

  @Autowired
  private SendLogService sendLogService;

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.eg.egsc.common.component.audit.service.AuditLogService#preRoutingFilter(javax.servlet.http
   * .HttpServletRequest)
   */
  @Override
  @SuppressWarnings("deprecation")
  public void preRoutingFilter(HttpServletRequest request) {
    String reqBody = null;
    AuditLogDto logDto = new AuditLogDto();
    if (request.getHeader("Content-Type") != null
        && request.getHeader("Content-Type").contains("multipart")) {
      reqBody = "file upload";
    } else {
      byte[] bytes = null;
      try {
        bytes = this.input2byte(request.getInputStream());
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      try {
        String sb = new String(bytes, "UTF-8");
        reqBody = URLDecoder.decode(sb.toString());
        logDto =
            JSON.parseObject(JSON.parseObject(reqBody).get("header").toString(), AuditLogDto.class);
      } catch (Exception e) {
        logger.error("Get requestHeader failed.");
      }
    }
    logDto.setRequestMessage(reqBody);
    logDto.setUrl(request.getRequestURL().toString());
    logDto.setIp(AuditUtil.getIpAddr(request));
    logDto.setType("interfacelog");
    long currentTime = System.currentTimeMillis();
    request.setAttribute("requestTime", currentTime);
    logDto.setOperateTime(new Date(currentTime));
    logDto.setOperation("1");
    logDto.setIntegrate("1");
    sendLogService.sendMsg(logDto);
  }

  @Override
  @SuppressWarnings("deprecation")
  public String postRoutingFilter(HttpServletRequest request, HttpServletResponse response,
      String responseData, boolean isDownload) {
    String reqBody = null;
    AuditLogDto logDto = new AuditLogDto();
    if ((request.getHeader("Content-Type") != null && request.getHeader("Content-Type").contains(
        "multipart"))) {
      reqBody = "file upload";
    } else if (isDownload) {
      reqBody = "file download";
    } else {
      byte[] bytes = null;
      try {
        bytes = this.input2byte(request.getInputStream());
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      try {
        String sb = new String(bytes, "UTF-8");
        reqBody = URLDecoder.decode(sb.toString());
        logDto =
            JSON.parseObject(JSON.parseObject(reqBody).get("header").toString(), AuditLogDto.class);
      } catch (Exception e) {
        logger.error("Get requestHeader failed.");
      }
    }
    if (responseData == null || response.getStatus() != 200 || responseData.indexOf("<html>") >= 0) {
      logDto.setIsSucess("0");
      logDto.setErrorDetail(responseData == null ? "Request fail." : responseData);
    } else {
      logDto.setResponseMessage(responseData);
      logDto.setIsSucess("1");
    }
    logDto.setRequestMessage(reqBody);
    logDto.setUrl(request.getRequestURL().toString());
    logDto.setIp(AuditUtil.getIpAddr(request));
    logDto.setType("interfacelog");
    logDto.setConsuming(String.valueOf(System.currentTimeMillis()
        - Long.parseLong(request.getAttribute("requestTime").toString())));
    logDto.setOperation("2");
    logDto.setIntegrate("1");
    sendLogService.sendMsg(logDto);
    return responseData;
  }

  public byte[] input2byte(InputStream inStream) throws IOException {
    ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
    byte[] buff = new byte[100];
    int rc = 0;
    while ((rc = inStream.read(buff, 0, 100)) > 0) {
      swapStream.write(buff, 0, rc);
    }
    byte[] in2b = swapStream.toByteArray();
    return in2b;
  }
}
