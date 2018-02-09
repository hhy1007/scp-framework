/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * redis配置属性类
 * 
 * @author songjie
 * @since 2018年1月17日
 */
@Component(value = "redisPropertiesFactory")
public class RedisProperties {
	private String maxRedirects;
	// 连接池中最大连接数。高版本：maxTotal，低版本：maxActive
	private String maxTotal;
	// 连接池中最大空闲的连接数
	private String maxIdle;
	// 连接池中最少空闲的连接数
	private String minIdle;
	// 当连接池资源耗尽时，调用者最大阻塞的时间，超时将跑出异常。单位，毫秒数;默认为-1.表示永不超时。高版本：maxWaitMillis，低版本：maxWait
	private String maxWaitMillis;
	// 连接空闲的最小时间，达到此值后空闲连接将可能会被移除。负值(-1)表示不移除
	private String minEvictableIdleTimeMillis;
	// 对于“空闲链接”检测线程而言，每次检测的链接资源的个数。默认为3
	private String numTestsPerEvictionRun;
	// “空闲链接”检测线程，检测的周期，毫秒数。如果为负值，表示不运行“检测线程”。默认为-1
	private String timeBetweenEvictionRunsMillis;
	// 向调用者输出“链接”资源时，是否检测是有有效，如果无效则从连接池中移除，并尝试获取继续获取。默认为false。建议保持默认值
	private String testOnBorrow;
	// 连超时设置
	private String timeout;
	// 是否使用连接池
	private String usePool;
	// host&port
	private String nodes;

	@Autowired
	private Environment env;

	/**
	 * 初始化redis配置
	 * 
	 * @Methods Name redisProperties
	 * @Create In 2018年1月16日 By songjie
	 * @return RedisProperties
	 */
	@Bean
	public RedisProperties redisProperties() {
		RedisProperties properties = new RedisProperties();
		maxRedirects = env.getProperty("default.redis.maxRedirects");
		maxTotal = env.getProperty("default.redis.maxTotal");
		maxIdle = env.getProperty("default.redis.maxIdle");
		minIdle = env.getProperty("default.redis.minIdle");
		maxWaitMillis = env.getProperty("default.redis.maxWaitMillis");
		minEvictableIdleTimeMillis = env
				.getProperty("default.redis.minEvictableIdleTimeMillis");
		numTestsPerEvictionRun = env
				.getProperty("default.redis.numTestsPerEvictionRun");
		timeBetweenEvictionRunsMillis = env
				.getProperty("default.redis.timeBetweenEvictionRunsMillis");
		testOnBorrow = env.getProperty("default.redis.testOnBorrow");
		timeout = env.getProperty("default.redis.timeout");
		usePool = env.getProperty("default.redis.usePool");
		nodes = env.getProperty("default.redis.nodes");

		properties.setMaxRedirects(maxRedirects);
		properties.setMaxTotal(maxTotal);
		properties.setMaxIdle(maxIdle);
		properties.setMinIdle(minIdle);
		properties.setMaxWaitMillis(maxWaitMillis);
		properties.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		properties.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
		properties
				.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		properties.setTestOnBorrow(testOnBorrow);
		properties.setTimeout(timeout);
		properties.setUsePool(usePool);
		properties.setNodes(nodes);
		return properties;
	}

	/**
	 * @return the maxRedirects
	 */
	public String getMaxRedirects() {
		return maxRedirects;
	}

	/**
	 * @param maxRedirects
	 *            the maxRedirects to set
	 */
	public void setMaxRedirects(String maxRedirects) {
		this.maxRedirects = maxRedirects;
	}

	/**
	 * @return the maxTotal
	 */
	public String getMaxTotal() {
		return maxTotal;
	}

	/**
	 * @param maxTotal
	 *            the maxTotal to set
	 */
	public void setMaxTotal(String maxTotal) {
		this.maxTotal = maxTotal;
	}

	/**
	 * @return the maxIdle
	 */
	public String getMaxIdle() {
		return maxIdle;
	}

	/**
	 * @param maxIdle
	 *            the maxIdle to set
	 */
	public void setMaxIdle(String maxIdle) {
		this.maxIdle = maxIdle;
	}

	/**
	 * @return the minIdle
	 */
	public String getMinIdle() {
		return minIdle;
	}

	/**
	 * @param minIdle
	 *            the minIdle to set
	 */
	public void setMinIdle(String minIdle) {
		this.minIdle = minIdle;
	}

	/**
	 * @return the maxWaitMillis
	 */
	public String getMaxWaitMillis() {
		return maxWaitMillis;
	}

	/**
	 * @param maxWaitMillis
	 *            the maxWaitMillis to set
	 */
	public void setMaxWaitMillis(String maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	/**
	 * @return the minEvictableIdleTimeMillis
	 */
	public String getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	/**
	 * @param minEvictableIdleTimeMillis
	 *            the minEvictableIdleTimeMillis to set
	 */
	public void setMinEvictableIdleTimeMillis(String minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	/**
	 * @return the numTestsPerEvictionRun
	 */
	public String getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}

	/**
	 * @param numTestsPerEvictionRun
	 *            the numTestsPerEvictionRun to set
	 */
	public void setNumTestsPerEvictionRun(String numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	/**
	 * @return the timeBetweenEvictionRunsMillis
	 */
	public String getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	/**
	 * @param timeBetweenEvictionRunsMillis
	 *            the timeBetweenEvictionRunsMillis to set
	 */
	public void setTimeBetweenEvictionRunsMillis(
			String timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	/**
	 * @return the testOnBorrow
	 */
	public String getTestOnBorrow() {
		return testOnBorrow;
	}

	/**
	 * @param testOnBorrow
	 *            the testOnBorrow to set
	 */
	public void setTestOnBorrow(String testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	/**
	 * @return the timeout
	 */
	public String getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the usePool
	 */
	public String getUsePool() {
		return usePool;
	}

	/**
	 * @param usePool
	 *            the usePool to set
	 */
	public void setUsePool(String usePool) {
		this.usePool = usePool;
	}

	/**
	 * @return the nodes
	 */
	public String getNodes() {
		return nodes;
	}

	/**
	 * @param nodes
	 *            the nodes to set
	 */
	public void setNodes(String nodes) {
		this.nodes = nodes;
	}

	/**
	 * 是否配置默认mq
	 * 
	 * @return
	 */
	public boolean validQueue() {
		return true;
	}
}
