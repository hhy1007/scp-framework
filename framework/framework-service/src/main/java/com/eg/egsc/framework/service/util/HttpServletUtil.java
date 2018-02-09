/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.eg.egsc.common.component.auth.web.FrontType;
import com.eg.egsc.common.component.utils.JsonUtil;
import com.eg.egsc.common.constant.CommonConstant;

/**
 * Servlet Utility
 * 
 * @author gaoyanlong
 * @since 2018年1月22日
 */
public class HttpServletUtil {

  private HttpServletUtil() {}

  private static final Logger logger = LoggerFactory.getLogger(HttpServletUtil.class);

  public static void initResponse(ServletResponse resp) {

    HttpServletResponse httpResponse = (HttpServletResponse) resp;
    httpResponse.setCharacterEncoding("UTF-8");
    httpResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    httpResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
    httpResponse.setHeader("Access-Control-Allow-Methods", "*");
    httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization");
    httpResponse.setHeader("Access-Control-Expose-Headers", "*");
  }

  /**
   * Response 401
   * 
   * @param request
   * @param response void
   */
  public static void populateResponse401(ServletRequest request, ServletResponse response) {
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
    PrintWriter print = null;
    try {
      print = response.getWriter();
    } catch (IOException e) {
      logger.error("PrintWriter error!", e);
    }
    String msgKey = "framework.auth.unauthorized";
    String jsonResponse = JsonUtil.toJson(MessageUtils.getResponseByMessageKey(msgKey,
        "非法请求【无效Authorization信息】", (HttpServletRequest) request));
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("Response: %s", jsonResponse));
    }
    print.write(jsonResponse);
  }

  /**
   * Is it owner visiting?
   * 
   * @param req
   * @return boolean
   */
  public static boolean isOwnerLogin(HttpServletRequest req) {
    boolean isFromOwner = false;
    String frontType = req.getHeader(CommonConstant.FRONT_TYPE);

    if (StringUtils.isEmpty(frontType)) {
      logger.error("Can't determine where is from, FrontType is not set in request header!");
    } else {
      if (frontType.equalsIgnoreCase(FrontType.EGC_OWNER_UI.getValue())
          || frontType.equalsIgnoreCase(FrontType.EGC_MOBILE_UI.getValue())) {
        isFromOwner = true;
      }
    }

    return isFromOwner;
  }
  

  /**
   * Is it from Egsc UI?
   * 
   * @param request
   * @return boolean
   */
  public static boolean isFromEgscUI(ServletRequest request) {
    String frontType = ((HttpServletRequest) request).getHeader("FrontType");
    if (StringUtils.isEmpty(frontType)) {
      logger.error("Can't determine where is from, FrontType is not set in request header!");
    } 
    return FrontType.SCP_ADMIN_UI.getValue().equalsIgnoreCase(frontType);
  }  

  /**
   * Response 403
   * 
   * @param request
   * @param response
   * @throws IOException void
   */
  public static void populateRespons403(ServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setStatus(HttpStatus.FORBIDDEN.value());

    PrintWriter print = response.getWriter();
    String msgKey = "framework.auth.login.forbidden";
    String jsonResponse = JsonUtil.toJson(
        MessageUtils.getResponseByMessageKey(msgKey, "用户登录失败", (HttpServletRequest) request));
    if (logger.isDebugEnabled()) {
      logger.debug("Response: %s ", jsonResponse);
    }

    print.write(jsonResponse);
  }

  /**
   * Response 500
   * 
   * @param code
   * @param message
   * @param req
   * @param resp void
   */
  public static void populateResponse500(String code, String msg, ServletRequest request,
      ServletResponse response) throws IOException {
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

    PrintWriter print = httpResponse.getWriter();
    String jsonResponse = JsonUtil
        .toJson(MessageUtils.getResponseByMessageKey(code, msg, (HttpServletRequest) request));
    if (logger.isDebugEnabled()) {
      logger.debug("Response: %s", jsonResponse);
    }

    print.write(jsonResponse);
  }
}
