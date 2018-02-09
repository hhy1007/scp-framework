/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.auth.adapter;

import com.eg.egsc.common.component.auth.exception.AuthException;
import com.eg.egsc.common.component.auth.model.User;

/**
 * IUserMgntAdaptor
 * 
 * @author gaoyanlong
 * @since 2018年1月3日
 */
public interface AuthAdapter {

  /**
   * 
   * 验证并返回用户信息
   * @param userName
   * @param password
   * @return
   * @throws AuthException User
   */
  User findUser(String userName, String password) throws AuthException;
  
  /**
   * 
   * 验证并返回用户信息
   * @param userName 手机号或者用户名
   * @param password
   * @param verifyCode
   * @return
   * @throws AuthException User
   */
  User findUser(String userName, String password, String verifyCode) throws AuthException;
}
