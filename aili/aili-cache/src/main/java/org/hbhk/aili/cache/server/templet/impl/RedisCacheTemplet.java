package org.hbhk.aili.cache.server.templet.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.cache.server.templet.ICacheTemplet;
import org.hbhk.aili.cache.share.pojo.KeyValue;
import org.hbhk.aili.cache.share.util.SerializeUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisCacheTemplet<V> implements ICacheTemplet<String, V> {
	private static final Log LOG = LogFactory.getLog(RedisCacheTemplet.class);
	private StringRedisTemplate StringRedisTemplate;

	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		StringRedisTemplate = stringRedisTemplate;
	}

	@Override
	public V get(String key) {
		if (StringUtils.isBlank((java.lang.String) key)) {
			LOG.error("key不允许为null或空串!");
			throw new RuntimeException("key不允许为null或空串!");
		}
		final String newKey = key;
		V value = StringRedisTemplate.execute(new RedisCallback<V>() {
			@SuppressWarnings("unchecked")
			@Override
			public V doInRedis(RedisConnection connection)
					throws DataAccessException {
				V obj = null;
				if (connection.exists(newKey.getBytes())) {
					byte[] values = connection.get(newKey.getBytes());
					if (values != null && values.length > 0) {
						obj = (V) SerializeUtil.deserializeObject(values);
					}
				}
				return obj;
			}
		});

		return value;
	}

	public void invalid(String... keys) {
		if (keys == null || keys.length == 0) {
			LOG.error("key不允许为null或空串!");
			throw new RuntimeException("key不允许为null或空串!");
		}
		final String[] newKeys = keys;
		StringRedisTemplate.execute(new RedisCallback<V>() {
			@Override
			public V doInRedis(RedisConnection connection)
					throws DataAccessException {
				for (String key : newKeys) {
					if (connection.exists(key.getBytes())) {
						connection.del(key.getBytes());
					}
				}
				return null;
			}

		});

	}

	@Override
	public boolean set(String key, V value, int expire) {
		try {
			if (StringUtils.isBlank(key) || value == null) {
				LOG.error("key或value不允许为null或空串!");
				throw new RuntimeException("key或value不允许为null或空串!");
			}
			final String newKey = key;
			final V newValue = value;
			final int newexpire = expire;
			StringRedisTemplate.execute(new RedisCallback<V>() {
				@Override
				public V doInRedis(RedisConnection connection)
						throws DataAccessException {
					// String valueStr = CacheUtils.toJsonString(newValue);
					byte[] bs = SerializeUtil.serializeObject(newValue);
					connection.setEx(newKey.getBytes(), newexpire, bs);
					return null;
				}

			});
			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return false;
		}

	}
	
	public boolean setNX(String key, V value, int expire) {
		try {
			if (StringUtils.isBlank(key) || value == null) {
				LOG.error("key或value不允许为null或空串!");
				throw new RuntimeException("key或value不允许为null或空串!");
			}
			final String newKey = key;
			final V newValue = value;
			final int newexpire = expire;
			boolean success = StringRedisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection)
						throws DataAccessException {
					// String valueStr = CacheUtils.toJsonString(newValue);
					byte[] bs = SerializeUtil.serializeObject(newValue);
					if(connection.exists(newKey.getBytes())){
						return false;
					}
					connection.setEx(newKey.getBytes(), newexpire, bs);
					return true;
				}

			});
			return success;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return false;
		}

	}
	
	
	public boolean setPi(final List<KeyValue<V>> keyValues, int expire) {
		try {
			final int newexpire = expire;
			boolean success = StringRedisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection connection)
						throws DataAccessException {
					connection.openPipeline();
					for (int i = 0; i < keyValues.size(); i++) {
						KeyValue<V> keyValue = keyValues.get(i);
						byte[] newKey = keyValue.getKey().getBytes();
						if(connection.exists(newKey)){
							continue ;
						}
						byte[] bs = SerializeUtil.serializeObject(keyValue.getValue());
						connection.setEx(newKey, newexpire, bs);
					}
					connection.openPipeline();
					return true;
				}

			});
			return success;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return false;
		}

	}


	@Override
	public void invalid(String key) {

	}


	@Override
	public boolean set(Map<String, V> values) {
		return false;
	}

	@Override
	public boolean set(Map<String, V> values, int expire) {
		return false;
	}

	@Override
	public long ttl(String key) {
		if (StringUtils.isBlank((java.lang.String) key)) {
			LOG.error("key不允许为null或空串!");
			throw new RuntimeException("key不允许为null或空串!");
		}
		final String newKey = key;
		Long ttl = StringRedisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				Long obj = 0L;
				if (connection.exists(newKey.getBytes())) {
					obj = connection.ttl(newKey.getBytes());
				}
				return obj;
			}
		});

		return ttl;
	}

	@Override
	public boolean set(String key, V value) {

		if (StringUtils.isBlank(key) || value == null) {
			LOG.error("key或value不允许为null或空串!");
			throw new RuntimeException("key或value不允许为null或空串!");
		}
		final String newKey = key;
		final V newValue = value;
		StringRedisTemplate.execute(new RedisCallback<V>() {
			@Override
			public V doInRedis(RedisConnection connection)
					throws DataAccessException {
				// String valueStr = CacheUtils.toJsonString(newValue);
				byte[] bs = SerializeUtil.serializeObject(newValue);
				connection.set(newKey.getBytes(), bs);
				return null;
			}
		});
		return false;
	}

	@Override
	public boolean isExitKey(String key) {
		final String newKey = key;
		boolean isExitKey = StringRedisTemplate
				.execute(new RedisCallback<Boolean>() {
					@Override
					public Boolean doInRedis(RedisConnection connection)
							throws DataAccessException {
						return connection.exists(newKey.getBytes());
					}
				});

		return isExitKey;
	}

}
