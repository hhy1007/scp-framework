/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.dao.intercepter;

/**
 * PostgreSQL方言实现类
 * 
 * @author gaoyanlong
 * @since 2018年1月19日
 */
public class PostgreSqlDialect extends Dialect {

	@Override
	public String getLimitSqlString(String sql, int skipResults, int maxResults) {
		sql = sql.replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ")
				+ " limit " + maxResults + " offset " + skipResults;
		return sql;
	}

	@Override
	public String getCountSqlString(String sql) {
		return PostgreSqlHelper.getCountString(sql);
	}

}
