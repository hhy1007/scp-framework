/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.auth.web;

/**
 * 平台类型
 * 
 * @author gaoyanlong
 * @since 2018年1月22日
 */
public enum FrontType {

  SCP_ADMIN_UI("scp-admin-ui"), SCP_EGSC_UI("scp-egsc-ui"), EGC_ADMIN_UI(
      "egc-admin-u"), EGC_MOBILE_UI("egc-mobile-ui"), EGC_OWNER_UI("egc-owner-ui");

  private String value;

  private FrontType(String value) {
    this.value = value;
  }

  /**
   * Return value
   * 
   * @return String
   */
  public String getValue() {
    return value;
  }
}
