/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.auth.model;

import java.io.Serializable;
import java.util.List;
/**
 * Menu
 * @author douguoqiang
 * @since 2018年1月15日
 */
public class Menu implements Serializable {
  /**
   * @Field long serialVersionUID 
   */
  private static final long serialVersionUID = 1L;
  private String id;
  private String title;
  private String url;
  private String icon;
  private List<Menu> submenus;
  private List<String> items;
  
  /**
   * @Return the String icon
   */
  public String getIcon() {
    return icon;
  }

  /**
   * @Param String icon to set
   */
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

  public List<Menu> getSubmenus() {
    return submenus;
  }

  public void setSubmenus(List<Menu> submenus) {
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
