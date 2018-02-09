/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.dao.intercepter;

import java.util.HashSet;
import java.util.Set;

/**
 * 数据库方言
 * 
 * @author gaoyanlong
 * @since 2018年1月19日
 */
public abstract class Dialect {

  public static enum Type {
    MYSQL, ORACLE, POSTGRESQL, H2;

    private static final Set<String> SUPPORTED_TYPE = new HashSet<String>();
    static {
      for (Type type : Type.values()) {
        SUPPORTED_TYPE.add(type.toString());
      }
    }

    public static Boolean supportingDatabaseType(String dbType) {
      return SUPPORTED_TYPE.contains(dbType.toUpperCase());
    }
  }

  public abstract String getLimitSqlString(String sql, int skipResults, int maxResults);

  public abstract String getCountSqlString(String sql);

}
