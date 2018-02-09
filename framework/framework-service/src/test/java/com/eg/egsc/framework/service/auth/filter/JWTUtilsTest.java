/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.auth.filter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eg.egsc.common.component.auth.utils.JWTUtils;

@RunWith(SpringJUnit4ClassRunner.class)
public class JWTUtilsTest {

  private static final Logger logger = LoggerFactory.getLogger(JWTUtilsTest.class);

  @Test
  public void testCheckToken() {
    String token = JWTUtils.getToken("xxx");
    logger.info(token);
    assertTrue(JWTUtils.checkToken(token).isSuccess());
    assertFalse(JWTUtils.checkToken(token + "1").isSuccess());
  }

  @Test
  public void testCheckExpiredToken() {

    String token = JWTUtils.getQuickToken("xxx", 10);
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    assertFalse(JWTUtils.checkToken(token).isSuccess());
  }
}
