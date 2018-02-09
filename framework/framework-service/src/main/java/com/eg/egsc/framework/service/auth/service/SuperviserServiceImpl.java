/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eg.egsc.common.component.auth.model.User;
import com.eg.egsc.common.component.auth.service.SuperviserService;
import com.eg.egsc.common.component.auth.utils.JWTUtils;
import com.eg.egsc.common.component.auth.web.SecurityContext;
import com.eg.egsc.common.component.redis.RedisUtils;
import com.eg.egsc.common.constant.CommonConstant;
import com.eg.egsc.framework.service.auth.adapter.CourtUuidAdapter;

/**
 * JWTAuthService
 * 
 * @author gaoyanlong
 * @since 2018年1月22日
 */
@Service
public class SuperviserServiceImpl implements SuperviserService {

  private static final Logger logger = LoggerFactory.getLogger(SuperviserServiceImpl.class);

  @Autowired
  private RedisUtils redisUtils;

  @Autowired
  private CourtUuidAdapter courtUuidAdapter;

  public void loginAsAdmin() {
    User user = newAdminUser();
    logDebug("Start to generate token with user id: %s", user.getUserId());
    String token = JWTUtils.getToken(user.getUserId());
    logDebug("End to generate token : %s", token);

    logDebug("Start to save user to redis");
    redisUtils.setOrigin(CommonConstant.REDIS_KEY_SSO_USER + token, user);
    logDebug("End to save user to redis : %s -> %s", CommonConstant.REDIS_KEY_SSO_USER + token,
        user);

    logDebug("Start to save user and token in context");
    SecurityContext.setUserPrincipal(user);
    SecurityContext.setToken(token);
    logDebug("End to save user and token in context");
  }

  /**
   * Create a new admin user
   * 
   * @return User
   */
  private User newAdminUser() {
    User user = new User();
    user.setUserId("admin");
    user.setUserName("admin");
    user.getServiceIds().add("/");
    user.setCourtUuid(courtUuidAdapter.getCurrentCourtUuid());

    return user;
  }

  private void logDebug(String format, Object... args) {
    if (logger.isDebugEnabled()) {
      logger.debug(String.format(format, args));
    }
  }
}
