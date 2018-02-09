/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.service.auth.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * TEST
 * 
 * @author gaoyanlong
 * @since 2018年1月18日
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class PermissionServiceTest {

  @Test
  public void testIsFromOtherApp() {
    assertTrue(new PermissionService().isApiURI("/scp-xx/api/xxx"));
    assertFalse(new PermissionService().isApiURI("/scp-xx/xxx"));
  }
}
