/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test Json Utility
 * 
 * @author gaoyanlong
 * @since 2018年1月23日
 */
public class Base64Test {

  private static final Logger logger = LoggerFactory.getLogger(Base64Test.class);

  @Test
  public void obj2json() {
    logger.info(Base64.encode("123456"));
    assertEquals("test", Base64.decode(Base64.encode("test")));
  }
}
