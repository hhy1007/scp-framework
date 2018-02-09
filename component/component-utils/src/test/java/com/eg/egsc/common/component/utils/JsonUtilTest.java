/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.utils;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Test Json Utility
 * 
 * @author gaoyanlong
 * @since 2018年1月23日
 */
public class JsonUtilTest {
  @Test
  public void obj2json() {
    assertNotNull(JsonUtil.toJson(new MyObj()));
  }
}


class MyObj {
  private int a;
  private String b = "";
  private List<MyObj> c = new ArrayList<MyObj>();

  public int getA() {
    return a;
  }

  public void setA(int a) {
    this.a = a;
  }

  public String getB() {
    return b;
  }

  public void setB(String b) {
    this.b = b;
  }

  public List<MyObj> getC() {
    return c;
  }

  public void setC(List<MyObj> c) {
    this.c = c;
  }

  /**
   * @param a
   * @param b
   * @param c
   */
  public MyObj(int a, String b, List<MyObj> c) {
    super();
    this.a = a;
    this.b = b;
    this.c = c;
  }

  /**
   * 
   */
  public MyObj() {
    super();
  }
}
