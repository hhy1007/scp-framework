/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.auth.web;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.eg.egsc.common.component.auth.model.User;
import com.eg.egsc.common.component.auth.web.vo.RoleVo;
import com.eg.egsc.common.component.auth.web.vo.UIResourceVo;
import com.eg.egsc.common.component.auth.web.vo.UserVo;
import com.eg.egsc.common.component.utils.JsonUtil;
/**
 * User Converter
 * 
 * @author gaoyanlong
 * @since 2018年1月8日
 */
public class UserConverter {

  private static final Logger logger = LoggerFactory.getLogger(UserConverter.class);

  public UserVo convert(User user) throws InstantiationException, IllegalAccessException {
    UserVo userVo = new UserVo();
    userVo.setRoles(new ArrayList<RoleVo>());
    userVo.setUiResources(new ArrayList<UIResourceVo>());
    BeanUtils.copyProperties(user, userVo);
    return userVo;
  }

  /*
   * private <S, T> void copyList(List<S> srcList, List<T> tgtList, Class<T> tgtClazz) throws
   */
  public static void main(String[] args) {
    User user = JsonUtil.load(
        "D:\\EGSC\\GIT\\developer-demo\\demo\\demo-service\\src\\test\\java\\mock_user.json",
        User.class);


    try {
      logger.info(new UserConverter().convert(user).toString());
    } catch (InstantiationException | IllegalAccessException e) {
      logger.error(e.getMessage(), e);
    }
  }
}
