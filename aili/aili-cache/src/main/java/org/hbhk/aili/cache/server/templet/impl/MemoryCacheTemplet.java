package org.hbhk.aili.cache.server.templet.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.cache.server.templet.ICacheTemplet;

public class MemoryCacheTemplet<V> implements ICacheTemplet<String, V> {
	private static final Log LOG = LogFactory.getLog(MemoryCacheTemplet.class);
	private Map<String, V> cache = new ConcurrentHashMap<String, V>(10000);

	@Override
	public boolean set(String key, V value) {
		try {
			if(StringUtils.isEmpty(key)){
				throw new RuntimeException("key不允许为null或空串!");
			}
			cache.put(key, value);
			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return false;
		}
		
	}
	@Override
	public boolean set(String key, V value, int expire) {
		return false;
	}

	@Override
	public V get(String key) {
		return cache.get(key);
	}

	@Override
	public void remove(String key) {
		if(StringUtils.isEmpty(key)){
			throw new RuntimeException("key不允许为null或空串!");
		}
		cache.remove(key);

	}

	@Override
	public void removeMulti(String... keys) {
		if(keys==null || keys.length==0){
			throw new RuntimeException("key不允许为null或空串!");
		}
		for (String key : keys) {
			cache.remove(key);
		}

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
		return 0;
	}

	@Override
	public boolean isExitKey(String key) {
		if(StringUtils.isEmpty(key)){
			throw new RuntimeException("key不允许为null或空串!");
		}
		return cache.containsKey(key);
	}

}
