/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.auth.web.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * MenuVo
 * @author douguoqiang
 * @since 2018年1月15日
 */
public class MenuVo implements Serializable {
  /**
   * @Field long serialVersionUID
   */
  private static final long serialVersionUID = 1L;
  private String id = "";
  private String title = "";
  private String url = "";
  private String icon = "";
  private List<MenuVo> submenus = new ArrayList<MenuVo>();
  private List<String> items = new ArrayList<String>();

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<MenuVo> getSubmenus() {
    return submenus;
  }

  public void setSubmenus(List<MenuVo> submenus) {
    this.submenus = submenus;
  }

  public List<String> getItems() {
    return items;
  }

  public void setItems(List<String> items) {
    this.items = items;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Menu [id=" + id + ", title=" + title + ", url=" + url + ", submenus=" + submenus
        + ", items=" + items + "]";
  }
}
