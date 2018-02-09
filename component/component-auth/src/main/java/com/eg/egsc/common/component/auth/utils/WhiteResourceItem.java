/**
 * Copyright 2017 - 2025 Evergrande Group
 */
package com.eg.egsc.common.component.auth.utils;

import java.util.List;

/**
 * Resource Data
 * 
 * @author gaoyanlong
 * @since 2018年1月17日
 */
public class WhiteResourceItem {

  private String fromfront;
  private List<String> fromips;
  private List<String> urls;

  public String getFromfront() {
    return fromfront;
  }

  public void setFromfront(String fromfront) {
    this.fromfront = fromfront;
  }

  public List<String> getFromips() {
    return fromips;
  }

  public void setFromips(List<String> fromips) {
    this.fromips = fromips;
  }

  public List<String> getUrls() {
    return urls;
  }

  public void setUrls(List<String> urls) {
    this.urls = urls;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ResourceInfo [fromfront=" + fromfront + ", fromips=" + fromips + ", urls=" + urls + "]";
  }
}
