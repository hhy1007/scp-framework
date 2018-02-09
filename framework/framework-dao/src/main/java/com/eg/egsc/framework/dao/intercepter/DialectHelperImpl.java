/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.dao.intercepter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 * 数据库方言辅助实现类
 * 
 * @author gaoyanlong
 * @since 2018年1月19日
 */
public class DialectHelperImpl implements DialectHelper, InitializingBean {

  private String dbType;
  private Dialect dialect;

  public void setDbType(String dbType) {
    this.dbType = dbType;
  }

  public Dialect getDialect() {
    return dialect;
  }

  private void setDialect() {
    if (dialect == null) {
      dialect = new PostgreSqlDialect();
    }
  }

  public void afterPropertiesSet() throws Exception {
    if (StringUtils.isBlank(dbType)) {
      throw new IllegalArgumentException("Property 'dbType' is required");
    }
    if (!Dialect.Type.supportingDatabaseType(dbType)) {
      throw new IllegalArgumentException(
          "Property 'dbType' value = '" + dbType + "' is unsupported");
    }
    setDialect();
  }
}
