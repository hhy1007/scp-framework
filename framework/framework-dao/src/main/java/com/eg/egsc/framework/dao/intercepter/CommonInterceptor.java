/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.dao.intercepter;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.eg.egsc.common.component.auth.model.User;
import com.eg.egsc.common.component.auth.web.SecurityContext;

/**
 * 公共字段持久化拦截器
 * 
 * @author songjie
 * @since 2018年1月19日
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = {
		MappedStatement.class, Object.class }) })
@Component
public class CommonInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(CommonInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		int sqlType = 0;
		Object[] args = invocation.getArgs();
		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];
			if (null == arg) {
				logger.info("arg["+i+"] is null, continue");
			}else if (arg instanceof MappedStatement) {
				MappedStatement ms = (MappedStatement) arg;
				SqlCommandType sqlCommandType = ms.getSqlCommandType();
				if (sqlCommandType == SqlCommandType.INSERT) {
					sqlType = 1;
				} else if (sqlCommandType == SqlCommandType.UPDATE) {
					sqlType = 2;
				} else {
					break;
				}
			} else {
				if (0 == sqlType) {
					continue;
				}
				Date currentDate = new Date();
				updateCommonFiled(arg, "UpdateTime", currentDate,
						Date.class);
				String userId = "admin";
				String courtUuid = "UNKOWN";
				User user = SecurityContext.getUserPrincipal();
				if (user != null) {
					if (StringUtils.isNotBlank(user.getUserId())) {
						userId = user.getUserId();
					}
					if (StringUtils.isNotBlank(user.getCourtUuid())) {
						courtUuid = user.getCourtUuid();
					}
				} else {
					logger.warn("login user is null");
				}
				updateCommonFiled(arg, "UpdateUser", userId, String.class);
				updateCommonFiled(arg, "CourtUuid", courtUuid, String.class);// 小区id
				if (1 == sqlType) {
					updateCommonFiled(arg, "CreateTime", currentDate,
							Date.class);
					updateCommonFiled(arg, "CreateUser", userId,
							String.class);
				}
			}
		}
		return invocation.proceed();
	}

	/**
	 * 
	 * @param arg
	 * @param clazz
	 * @param currentDate
	 *            void
	 */
	private void updateCommonFiled(Object arg, String filedName,
			Object filedValue, Class<?> cla) {
    Class<? extends Object> clazz = arg.getClass();
		try {
			Method getFiled = clazz.getDeclaredMethod("get" + filedName);
			Method setFiled = clazz.getDeclaredMethod("set" + filedName, cla);
			Object value = getFiled.invoke(arg);
			if ((cla == Date.class && null == value && null != filedValue)
					|| (cla == String.class
							&& StringUtils.isBlank((String) value) && StringUtils
								.isNotBlank((String) filedValue))) {
				setFiled.invoke(arg, filedValue);
			}
		} catch (Throwable e) {
			logger.info(String.format("Java反射调用失败,调用类%s中没有 get%s或 set%s方法", clazz.getName(), filedName, filedName));
		}
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// Do nothing
	}
}
