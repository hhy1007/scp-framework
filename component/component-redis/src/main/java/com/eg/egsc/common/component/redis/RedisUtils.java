/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.redis;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import com.eg.egsc.common.component.utils.DateUtils;
import com.eg.egsc.common.config.ConfigProperties;

/**
 * redis工具类
 * 
 * @author songjie
 * @since 2018年1月17日
 */
@Component
public class RedisUtils {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private ConfigProperties configProperties;

	/**30s 超时时间*/
	private static final long DEFAULT_EXPIRE = 30;
	private static final Logger logger = LoggerFactory
			.getLogger(RedisUtils.class);

	/**
	 * 将value对象写入缓存
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value) {
		if (null != key) {
			redisTemplate.opsForValue().set(getKey(key), value);
		}
	}

	/**
	 * 将value对象写入缓存
	 * 
	 * @param key
	 * @param value
	 * @param time
	 */
	public void set(String key, Object value, long time) {
		if (null != key) {
			set(key, value);
			expire(key, time);
		}
	}

	/**
	 * get对象
	 * 
	 * @param key
	 * @return Object
	 */
	public Object get(String key) {
		if (null != key) {
			return redisTemplate.opsForValue().get(getKey(key));
		}
		return null;
	}

	/**
	 * 删除缓存 根据key精确匹配删除
	 * 
	 * @param key
	 */
	public void del(String... key) {
		if (key != null && key.length > 0) {
			List<String> delKeys = new ArrayList<String>();
			for (String k : key) {
				if (StringUtils.isNotBlank(k)) {
					delKeys.add(getKey(k));
					redisTemplate.delete(getKey(k));
				}
			}
			redisTemplate.delete(delKeys);
		}
	}

	/**
	 * 指定缓存的失效时间
	 * 
	 * @param key
	 * @param time
	 */
	public void expire(String key, long time) {
		if (time > 0) {
			redisTemplate.expire(getKey(key), time, TimeUnit.SECONDS);
		}
	}

	/**
	 * 生成seq
	 * 
	 * @param key
	 * @return long
	 */
	public long generate(String key) {
		RedisAtomicLong counter = new RedisAtomicLong(getKey(key),
				redisTemplate.getConnectionFactory());
		return counter.incrementAndGet();
	}

	/**
	 * 生成seq，设置失效时间
	 * 
	 * @param key
	 * @param expireTime
	 * @return long
	 */
	public long generate(String key, Date expireTime) {
		RedisAtomicLong counter = new RedisAtomicLong(getKey(key),
				redisTemplate.getConnectionFactory());
		counter.expireAt(expireTime);
		return counter.incrementAndGet();
	}

	/**
	 * 按给定增长额生成seq
	 * 
	 * @param key
	 * @param increment
	 * @return long
	 */
	public long generate(String key, int increment) {
		RedisAtomicLong counter = new RedisAtomicLong(getKey(key),
				redisTemplate.getConnectionFactory());
		return counter.addAndGet(increment);
	}

	/**
	 * 按给定增长额生成seq，设置失效时间
	 * 
	 * @param key
	 * @param increment
	 * @param expireTime
	 * @return long
	 */
	public long generate(String key, int increment, Date expireTime) {
		RedisAtomicLong counter = new RedisAtomicLong(getKey(key),
				redisTemplate.getConnectionFactory());
		counter.expireAt(expireTime);
		return counter.addAndGet(increment);
	}

	/**
	 * 获取key的前缀-应用简称:
	 * 
	 * @return StringBuffer
	 */
	private StringBuilder getPreKey() {
		StringBuilder buffer = new StringBuilder("");
		String shortname = configProperties.getAppshortname();
		if (StringUtils.isNotBlank(shortname)) {
			buffer.append(shortname).append(":");
		}
		return buffer;
	}

	/**
	 * 获取加上前缀的key
	 * 
	 * @param key
	 * @return String
	 */
	private String getKey(String key) {
		if (StringUtils.isNotBlank(key)) {
			return getPreKey().append(key).toString();
		}
		return key;
	}

	/**
	 * 生成lock key
	 * 
	 * @param key
	 * @return String
	 */
	public String generateLockKey(String key) {
		return String.format("LOCK:%s", getKey(key));
	}

	/**
	 * 将value对象写入缓存
	 * 
	 * @param key
	 * @param value
	 *            void
	 */
	public void setOrigin(String key, Object value) {
		if (null != key) {
			redisTemplate.opsForValue().set(key, value);
		}
	}

	/**
	 * 将value对象写入缓存
	 * 
	 * @param key
	 * @param value
	 * @param time
	 *            void
	 */
	public void setOrigin(String key, Object value, long time) {
		if (null != key) {
			setOrigin(key, value);
			if (time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
		}
	}

	/**
	 * get对象
	 * 
	 * @param key
	 * @return Object
	 */
	public Object getOrigin(String key) {
		if (null != key) {
			return redisTemplate.opsForValue().get(key);
		}
		return null;
	}

	/**
	 * 删除缓存 根据key精确匹配删除
	 * 
	 * @param key
	 *            void
	 */
	public void delOrigin(String... key) {
		if (key != null && key.length > 0) {
			List<String> delKeys = new ArrayList<String>();
			for (String k : key) {
				if (StringUtils.isNotBlank(k)) {
					delKeys.add(k);
					redisTemplate.delete(k);
				}
			}
			redisTemplate.delete(delKeys);
		}
	}
	
	/**
	 * 加锁（key带应用简称）
	 * 
	 * @param key
	 * @return boolean
	 */
	public boolean lock(String key) {
		return lock(key, DEFAULT_EXPIRE);
	}
	
	/**
	 * 加锁（key带应用简称）
	 * @param key
	 * @param time 超时时间（秒）
	 * @return boolean
	 */
	public boolean lock(String key, long time) {
		return lockOrigin(generateLockKey(key), time);
	}

	/**
	 * 解锁（key带应用简称）
	 * 
	 * @param key
	 *            void
	 */
	public void unlock(String key) {
		unlockOrigin(generateLockKey(key));
	}
	
	/**
	 * 加锁
	 * @param key 加锁key
	 * @param time 超时时间（秒）
	 * @return boolean 成功 true，失败 fasle
	 */
	public boolean lockOrigin(String key, long time) {
		if (StringUtils.isBlank(key)) {
			return false;
		}
		try {
			RedisSerializer<String> serializer = redisTemplate
					.getStringSerializer();
			if (redisTemplate.getConnectionFactory().getConnection()
					.setNX(serializer.serialize(key), new byte[0])) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);// 暂设置过期，防止异常中断锁未释放
				logger.debug("add RedisLock[" + key + "].");
				return true;
			} 
		} catch (Exception e) {
			unlockOrigin(key);
		}
		return false;
	}


	/**
	 * 解锁
	 * 
	 * @param key
	 *            void
	 */
	public void unlockOrigin(String key) {
		if (StringUtils.isBlank(key)) {
			return;
		}
		logger.debug("release RedisLock[" + key + "].");
		RedisSerializer<String> serializer = redisTemplate
				.getStringSerializer();
		redisTemplate.getConnectionFactory()
				.getConnection().del(serializer.serialize(key));
	}
	
	/**
	 * 加锁
	 * 
	 * @param key
	 * @param time 有效期（秒）
	 * @return boolean
	 */
	public boolean lockBusiness(String key, long time) {
		String lockKey = generateLockKey(key);
		if(lockOrigin(lockKey, time)){
			set(lockKey, DateUtils.getDate("yyyy-MM-dd HH:mm:ss"));
			return true;
		}
		return false;
	}

	/**
	 * 加锁的key是否有效
	 * @param key
	 * @param time 有效期（秒）
	 * @return boolean
	 */
	public boolean isvalidLockBusiness(String key, long time) {
		String lockKey = generateLockKey(key);
		Object value = get(lockKey);
		if (null != value && !"".equals(value)) {
			try {
				long times = DateUtils.pastSeconds(DateUtils.parseDate(
						value.toString(), "yyyy-MM-dd HH:mm:ss"));
				if (times < time) {
					return true;
				}
			} catch (ParseException e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * 解锁
	 * 
	 * @param key
	 *            void
	 */
	public void unlockBusiness(String key) {
		String lockKey = generateLockKey(key);
		unlockOrigin(lockKey);
		del(lockKey);
	}
}
