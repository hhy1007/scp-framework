/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.auth.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class RequestIgnoreListTest {
  // 拼接IP
  public static final String IP1 = "127.".concat("0.").concat("0.").concat("1");
  public static final String IP2 = "127.".concat("0.").concat("0.").concat("2");
  private static final String SIGNUP = "/signup";
  private static final String EGC_MOBILE_UI = "egc-mobile-ui";
  private static final String ACCOUNT_SIGNUP = "/api/account/signup";
  private static final String SIGNUP_PATH = "http://xxx:9090/demo/api/account/signup";

  @Test
  public void testUrlIgnoreList() {

    assertTrue(RequestIgnoreList.containsUrl("http://xxx:9090/login"));
    assertTrue(RequestIgnoreList.containsUrl("http://xxx:9090/login?xx"));
    assertTrue(RequestIgnoreList.containsUrl("http://xxx:9090/login/xx"));
    assertTrue(RequestIgnoreList.containsUrl("http://xxx:9090/xx/login/xx"));
    assertFalse(RequestIgnoreList.containsUrl("http://xxx:9090/xx/xx"));
  }

  @SuppressWarnings("static-access")
  @Test
  public void testContains() {
    List<String> whiteList = Arrays.asList(new String[] {ACCOUNT_SIGNUP});
    RequestIgnoreList urlIgnoreList = new RequestIgnoreList();
    assertTrue(urlIgnoreList.contains(whiteList, SIGNUP_PATH));

    whiteList = Arrays.asList(new String[] {IP2, IP1});
    assertTrue(urlIgnoreList.contains(whiteList, IP1));

    whiteList = Arrays.asList(new String[] {IP2});
    assertFalse(urlIgnoreList.contains(whiteList, IP1));
  }

  @Test
  public void testMatch_no_any_setting() {
    RequestIgnoreList urlIgnoreList = new RequestIgnoreList();
    WhiteResourceItem wr = new WhiteResourceItem();
    assertFalse(urlIgnoreList.match(wr, EGC_MOBILE_UI, IP1, SIGNUP_PATH));
  }

  @Test
  public void testMatch_url() {
    RequestIgnoreList urlIgnoreList = new RequestIgnoreList();
    WhiteResourceItem wr = new WhiteResourceItem();
    wr.setUrls(Arrays.asList(new String[] {ACCOUNT_SIGNUP}));
    assertFalse(urlIgnoreList.match(wr, null, null, "/demoxx"));
    
    assertTrue(urlIgnoreList.match(wr, EGC_MOBILE_UI, IP1, SIGNUP_PATH));

    wr.setUrls(Arrays.asList(new String[] {ACCOUNT_SIGNUP, "/demo"}));
    assertTrue(urlIgnoreList.match(wr, EGC_MOBILE_UI, IP1, SIGNUP_PATH));

  }

  @Test
  public void testMatch_ip() {
    RequestIgnoreList urlIgnoreList = new RequestIgnoreList();
    WhiteResourceItem wr = new WhiteResourceItem();
    wr.setUrls(Arrays.asList(new String[] {SIGNUP}));

    wr.setFromips(Arrays.asList(new String[] {IP2}));
    assertFalse(urlIgnoreList.match(wr, EGC_MOBILE_UI, IP1, SIGNUP_PATH));

    wr.setFromips(Arrays.asList(new String[] {IP2, IP1}));
    assertTrue(urlIgnoreList.match(wr, EGC_MOBILE_UI, IP1, SIGNUP_PATH));
  }

  @Test
  public void testMatch_fronttype() {
    RequestIgnoreList urlIgnoreList = new RequestIgnoreList();
    WhiteResourceItem wr = new WhiteResourceItem();
    assertFalse(urlIgnoreList.match(wr, EGC_MOBILE_UI, IP1, SIGNUP_PATH));

    wr.setFromfront("egscUI");
    assertFalse(urlIgnoreList.match(wr, EGC_MOBILE_UI, IP1, SIGNUP_PATH));

    wr.setFromfront(EGC_MOBILE_UI);
    assertFalse(urlIgnoreList.match(wr, EGC_MOBILE_UI, IP1, SIGNUP_PATH));

    wr.setFromfront(EGC_MOBILE_UI);
    wr.setUrls(Arrays.asList(new String[] {SIGNUP}));
    assertTrue(urlIgnoreList.match(wr, EGC_MOBILE_UI, IP1, SIGNUP_PATH));
  }



  @Test
  public void testMatch_fronttype_not_set_in_request() {
    RequestIgnoreList urlIgnoreList = new RequestIgnoreList();
    WhiteResourceItem wr = new WhiteResourceItem();
    wr.setFromfront(EGC_MOBILE_UI);
    wr.setUrls(Arrays.asList(new String[] {SIGNUP}));
    assertFalse(urlIgnoreList.match(wr, "", IP1, SIGNUP_PATH));
  }

  @Test
  public void testMatch_url_all() {
    RequestIgnoreList urlIgnoreList = new RequestIgnoreList();
    WhiteResourceItem wr = new WhiteResourceItem();
    wr.setFromips(Arrays.asList(new String[] {}));
    wr.setUrls(Arrays.asList(new String[] {"/"}));

    assertTrue(urlIgnoreList.match(wr, "", IP1, SIGNUP_PATH));
  }
}
