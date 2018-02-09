/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.dao.intercepter;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分页拦截器实现类
 * 
 * @author gaoyanlong
 * @since 2018年1月19日
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare",
    args = {Connection.class, Integer.class})})
public class PaginationInterceptor implements Interceptor {
  private static final Logger logger = LoggerFactory.getLogger(PaginationInterceptor.class);
  private DialectHelper dialectHelper;

  public Object intercept(Invocation invocation) throws Throwable {
    StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

    MetaObject metaStatementHandler =
        MetaObject.forObject(statementHandler, new DefaultObjectFactory(),
            new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());

    RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
    if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
      return invocation.proceed();
    }
    String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
    metaStatementHandler.setValue("delegate.boundSql.sql", dialectHelper.getDialect()
        .getLimitSqlString(originalSql, rowBounds.getOffset(), rowBounds.getLimit()));
    metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
    metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);

    return invocation.proceed();
  }

  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {
    if (logger.isDebugEnabled() && properties != null) {
      logger.debug(properties.toString());
    }
  }

  public void setDialectHelper(DialectHelper dialectHelper) {
    this.dialectHelper = dialectHelper;
  }

}
