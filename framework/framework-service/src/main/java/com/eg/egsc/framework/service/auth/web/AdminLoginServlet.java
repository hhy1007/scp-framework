/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.auth.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
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

import com.eg.egsc.common.component.auth.adapter.AuthAdapter;
import com.eg.egsc.common.component.auth.model.User;
import com.eg.egsc.common.component.auth.utils.JWTUtils;
import com.eg.egsc.common.component.auth.web.UserConverter;
import com.eg.egsc.common.component.auth.web.vo.UserVo;
import com.eg.egsc.common.component.redis.RedisUtils;
import com.eg.egsc.common.component.utils.JsonUtil;
import com.eg.egsc.common.constant.CommonConstant;
import com.eg.egsc.common.exception.CommonException;
import com.eg.egsc.framework.client.dto.ResponseDto;
import com.eg.egsc.framework.service.auth.adapter.CourtUuidAdapter;
import com.eg.egsc.framework.service.util.HttpServletUtil;

/**
 * Admin Login Servlet
 * 
 * @author gaoyanlong
 * @since 2018年1月22日
 */
@WebServlet(urlPatterns = "/admin/login")
public class AdminLoginServlet extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(AdminLoginServlet.class);

  @Autowired
  private AuthAdapter userMgmtAuthAdapterImpl;
  @Autowired
  private AuthAdapter ownerInfoMgmtAuthAdapterImpl;
  @Autowired
  private CourtUuidAdapter courtUuidAdapter;

  @Autowired
  private RedisUtils redisUtils;

  /**
   * @Field long serialVersionUID
   */
  private static final long serialVersionUID = 1L;

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
   */
  @Override
  public void init(ServletConfig config) throws ServletException {
    logger.info("init(config)");
    super.init(config);
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.GenericServlet#init()
   */
  @Override
  public void init() throws ServletException {
    logger.info("init");
    super.init();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    logger.info("doGet");
    doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    HttpServletUtil.initResponse(resp);

    // Get username/password from request body
    String username = req.getParameter("username");
    String password = req.getParameter("password");
    String verifycode = req.getParameter("verifycode");
    logDebug("Get user (%s/%s) from request", username, password);

    User user = null;
    try {
      if (HttpServletUtil.isOwnerLogin(req)) {
        logDebug("Owner mgnt login");
        user = getValidOwnerInfo(username, password, verifycode);
      } else {
        logDebug("User mgnt login");
        user = getValidUserInfo(username, password);
        setCourtUuid(user);
      }

      // if invalid user
      if (user == null) {
        // return login failure response
        HttpServletUtil.populateRespons403(req, resp);
        return;
      }

      // if valid user
      else {
        // Generate jwt token
        logDebug("Start to generate token with user id:", user.getUserId());
        String token = JWTUtils.getToken(user.getUserId());
        logDebug("End to generate token");

        logDebug("Start to save user to redis");
        redisUtils.setOrigin(CommonConstant.REDIS_KEY_SSO_USER + token, user);
        logDebug("End to save user to redis : %s -> %s", CommonConstant.REDIS_KEY_SSO_USER + token,
            user);

        // Save jwt token to cookie
        logDebug("Start to save token to cookie");
        Cookie jwtCookie = new Cookie(CommonConstant.TOKEN_COOKIE_NAME, token);

        // Security
        jwtCookie.setSecure(true);

        jwtCookie.setMaxAge(60 * CommonConstant.JWT_MAX_AGE_MINUTES);
        // The cookie is active for all path
        jwtCookie.setPath("/");
        resp.addCookie(jwtCookie);
        logDebug("End to save token to cookie: %s", jwtCookie);

        // return response with user data and token
        UserVo userVo = new UserConverter().convert(user);
        userVo.setToken(token);
        populateResponse200(token, userVo, resp);
      }
    } catch (CommonException e) {
      logger.error(e.getMessage(), e);
      HttpServletUtil.populateResponse500(e.getCode(), e.getMessage(), req, resp);
      return;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      HttpServletUtil.populateResponse500(CommonConstant.MESSAGE_KEY_INTERNAL_ERROR, e.getMessage(),
          req, resp);
      return;
    }
  }

  /**
   * Set Court uuid
   * 
   * @param user void
   */
  private void setCourtUuid(User user) {
    if (user != null) {
      String courtUuid = courtUuidAdapter.getCurrentCourtUuid();
      if (courtUuid == null) {
        throw new CommonException(null, "无法取得小区UUID!");
      }
      user.setCourtUuid(courtUuid);
    }
  }

  /**
   * 用户管理
   * 
   * @param username
   * @param password
   * @return User
   */
  protected User getValidUserInfo(String username, String password) {
    logDebug("Start to retrieve user from user management service");
    User user = userMgmtAuthAdapterImpl.findUser(username, password);
    logDebug("End to retrieve user from user management service with result: %s", user);
    return user;
  }

  /**
   * 业主管理
   * 
   * @param username
   * @param password
   * @param verifyCode
   * @return User
   */
  protected User getValidOwnerInfo(String username, String password, String verifyCode) {
    logDebug("Start to retrieve user from owner info management service");
    User user = ownerInfoMgmtAuthAdapterImpl.findUser(username, password, verifyCode);
    logDebug("End to retrieve user from owner info management service with result: ", user);
    return user;
  }

  private void populateResponse200(String token, UserVo user, HttpServletResponse resp)
      throws IOException {
    resp.setStatus(HttpStatus.OK.value());

    PrintWriter print = resp.getWriter();
    String jsonResponse = JsonUtil.toJson(new ResponseDto(CommonConstant.SUCCESS_CODE, user, ""));
    logDebug("Response: %s", jsonResponse);
    logDebug("token: %s", token);
    print.write(jsonResponse);
  }

  private void logDebug(String format, Object... args) {
    if (logger.isDebugEnabled()) {
      logger.debug(String.format(format, args));
    }
  }
}
