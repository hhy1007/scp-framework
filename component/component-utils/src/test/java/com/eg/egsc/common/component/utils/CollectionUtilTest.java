/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author guofeng
 * @since 2018年1月18日
 */
public class CollectionUtilTest {

  private static final Logger logger = LoggerFactory.getLogger(CollectionUtilTest.class);

  public void print(Object obj) {
    logger.info(obj.toString());
  }

  @Test
  public void testCollectionUtil() {
    String[] strArrNull = null;
    print(CollectionUtil.isBlank(strArrNull));
    print(CollectionUtil.isNotBlank(strArrNull));
    String[] strArr = new String[0];
    print(CollectionUtil.isBlank(strArr));
    print(CollectionUtil.isNotBlank(strArr));
    String[] strArrNotNull = new String[1];
    print(CollectionUtil.isNotBlank(strArrNotNull));
    print(CollectionUtil.isBlank(strArrNotNull));

    Object strNull = null;
    print(CollectionUtil.isBlank(strNull));
    print(CollectionUtil.isNotBlank(strNull));
    Object str = "";
    print(CollectionUtil.isBlank(str));
    print(CollectionUtil.isNotBlank(str));
    Object strNotNull = "1";
    print(CollectionUtil.isNotBlank(strNotNull));
    print(CollectionUtil.isBlank(strNotNull));

    List<String> strListNull = null;
    print(CollectionUtil.isBlank(strListNull));
    print(CollectionUtil.isNotBlank(strListNull));
    List<String> strList = new ArrayList<String>();
    print(CollectionUtil.isBlank(strList));
    print(CollectionUtil.isNotBlank(strList));
    List<String> strListNotNull = new ArrayList<String>();
    strListNotNull.add("1");
    print(CollectionUtil.isNotBlank(strListNotNull));
    print(CollectionUtil.isBlank(strListNotNull));

    Set<String> strSetNull = null;
    print(CollectionUtil.isBlank(strSetNull));
    print(CollectionUtil.isNotBlank(strSetNull));
    Set<String> strSet = new HashSet<String>();
    print(CollectionUtil.isBlank(strSet));
    print(CollectionUtil.isNotBlank(strSet));
    Set<String> strSetNotNull = new HashSet<String>();
    strSetNotNull.add("1");
    print(CollectionUtil.isNotBlank(strSetNotNull));
    print(CollectionUtil.isBlank(strSetNotNull));

    Serializable serNull = null;
    print(CollectionUtil.isBlank(serNull));
    print(CollectionUtil.isNotBlank(serNull));
    Serializable serNotNull = "1";
    print(CollectionUtil.isNotBlank(serNotNull));
    print(CollectionUtil.isBlank(serNotNull));

    Map<String, String> strMapNull = null;
    print(CollectionUtil.isBlank(strMapNull));
    print(CollectionUtil.isNotBlank(strMapNull));
    Map<String, String> strMap = new HashMap<String, String>();
    print(CollectionUtil.isBlank(strMap));
    print(CollectionUtil.isNotBlank(strMap));
    Map<String, String> strMapNotNull = new HashMap<String, String>();
    strMapNotNull.put("1", "1");
    print(CollectionUtil.isNotBlank(strMapNotNull));
    print(CollectionUtil.isBlank(strMapNotNull));
  }
}
