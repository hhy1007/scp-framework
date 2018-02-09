/**
 * Copyright 2017 - 2025 Evergrande Group
 */
package com.eg.egsc.common.component.auth.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 白名单
 * 
 * @author gaoyanlong
 * @since 2018年1月17日
 */
@Component
@ConfigurationProperties(prefix = "egsc.config.auth.white")
public class WhiteResourceList {
  private List<WhiteResourceItem> list = new ArrayList<>();

  public List<WhiteResourceItem> getList() {
    return list;
  }

  public void setList(List<WhiteResourceItem> list) {
    this.list = list;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "WhiteList [list=" + list + "]";
  }
}
