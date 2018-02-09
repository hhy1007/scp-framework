/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.auth.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * 采用Spring Security进行加密解密的工具类，标准加密策略已经非常安全，故不做其他扩展
 * 
 * @author douguoqiang
 * @since 2018年1月11日
 */
public class EncryptUtil {
  
  private EncryptUtil() {
  }

  private static final String SITE_WIDE_SECRET = "35637289C2354E38B5FC743983DFBDD6";
	
	private static final PasswordEncoder ENCODER = new StandardPasswordEncoder(SITE_WIDE_SECRET);

	public static String encrypt(String rawPassword) {
		return ENCODER.encode(rawPassword);
	}

	public static boolean match(String rawPassword, String password) {
		return ENCODER.matches(rawPassword, password);
	}
}
