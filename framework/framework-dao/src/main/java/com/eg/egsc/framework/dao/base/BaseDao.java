/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.dao.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eg.egsc.framework.paging.PageUtils;

/**
 * 所有Component Service实现类的基类，用于提供所有Component Service实现类的基本特征功能
 * 
 * @author douguoqiang
 * @since 2016-5-27
 */
public abstract class BaseDao<M, M1, E> extends SqlSessionDaoSupport {

	protected Class<M> mapperClass;

	protected Class<M1> extMapperClass;

	protected Class<E> entityClass;

	protected SqlSessionFactory sqlSessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);

	/**
	 * 构造方法
	 */
	protected BaseDao() {
		setMapperClass();
		setEntityClass();
	}

	/**
	 * 子类必须实现的方法，用于指定Mapper的Class类型
	 */
	protected abstract void setMapperClass();

	/**
	 * 子类必须实现的方法，用于指定实体类的Class类型
	 */
	protected abstract void setEntityClass();

	/**
	 * 指定Mybatis代码生成器生成的Mapper类的Class类型
	 */
	protected void setMapperClass(Class<M> mapperClass) {
		this.mapperClass = mapperClass;
	}

	/**
	 * 指定自定义的Mapper类的Class类型
	 */
	protected void setExtMapperClass(Class<M1> extMapperClass) {
		this.extMapperClass = extMapperClass;
	}

	/**
	 * 指定实体类的Class类型
	 * 
	 * @param entityClass
	 *            实体类Class类型
	 */
	protected void setEntityClass(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * 获取DAO关联的XxxMapper对象
	 * 
	 * @return DAO关联的XxxMapper对象
	 */
	public M getMapper() {
		return this.getSqlSession().getMapper(mapperClass);
	}

	/**
	 * 获取DAO关联的Xxx1Mapper对象
	 * 
	 * @return DAO关联的Xxx1Mapper对象
	 */
	public M1 getExtMapper() {
		return this.getSqlSession().getMapper(extMapperClass);
	}

	/**
	 * 获取JDBC Connection对象
	 * 
	 * @return JDBC Connection对象
	 */
	public Connection getJDBCConnection() {
		return this.getSqlSession().getConnection();
	}

	protected long getRecordTotal(String whereclause) {
		long recordTotal = 0;
		Connection conn = this.getSqlSession().getConnection();

		String tableName = PageUtils
				.underscoreName(entityClass.getSimpleName());
		StringBuilder sb = new StringBuilder("select count(*) from ")
				.append(tableName);
		if (StringUtils.isNotBlank(whereclause)) {
			sb.append(" where 1=1 ").append(whereclause);
		}
		try (Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sb.toString())) {
			if (rs.next()) {
				recordTotal = rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return recordTotal;
	}

	protected long getSID() {
		int recordTotal = 0;
		Connection conn = this.getSqlSession().getConnection();

		String tableName = PageUtils
				.underscoreName(entityClass.getSimpleName());
		String sequenceName = tableName + "_S";
		StringBuilder sb = new StringBuilder("select ").append(sequenceName)
				.append(".nextVal from dual");
		try (Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sb.toString())) {
			if (rs.next()) {
				recordTotal = rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return recordTotal;
	}

	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
}
