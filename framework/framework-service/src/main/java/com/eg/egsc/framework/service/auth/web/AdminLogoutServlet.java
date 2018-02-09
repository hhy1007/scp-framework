/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.auth.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.eg.egsc.common.component.redis.RedisUtils;
import com.eg.egsc.common.component.utils.JsonUtil;
import com.eg.egsc.common.constant.CommonConstant;
import com.eg.egsc.framework.client.dto.ResponseDto;

@WebServlet(urlPatterns = "/admin/logout")
public class AdminLogoutServlet extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(AdminLogoutServlet.class);

  /**
   * @Field long serialVersionUID
   */
  private static final long serialVersionUID = 1L;

  private static final int JWT_MIN_AGE_MINUTES = 0;

  @Autowired
  private RedisUtils redisUtils;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    logger.error("Save user to reids: not implement now!");

    // destroy jwt token
    Cookie jwtCookie = new Cookie(CommonConstant.TOKEN_COOKIE_NAME, null);
    jwtCookie.setMaxAge(JWT_MIN_AGE_MINUTES);
    jwtCookie.setPath("/");
    resp.addCookie(jwtCookie);
    logDebug("clean token in cookie: %s", jwtCookie);

    // clean user from redis
    HttpServletRequest httpRequest = (HttpServletRequest) req;
    String token = httpRequest.getHeader("Authorization");
    logDebug("token : %s", token);
    redisUtils.del(CommonConstant.REDIS_KEY_SSO_USER + token);

    // return response
    return200(resp);
  }


  /**
   * @Methods Name return200
   * @param user
   * @param resp void
   * @throws IOException
   */
  private void return200(HttpServletResponse resp) throws IOException {
    resp.setStatus(HttpStatus.OK.value());

    PrintWriter print = resp.getWriter();
    String jsonResponse =
        JsonUtil.toJson(new ResponseDto(CommonConstant.SUCCESS_CODE, null, "用户登出成功"));
    logDebug("Response: %s", jsonResponse);

    print.write(jsonResponse);
  }

  private void logDebug(String format, Object... args) {
    if (logger.isDebugEnabled()) {
      logger.debug(String.format(format, args));
    }
  }
}
