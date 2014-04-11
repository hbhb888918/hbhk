package org.hbhk.aili.cache.server;


public class CacheSupportTest  extends  CacheSupport<String>{

	@Override
	public String getCacheId() {
		
		return "hbhk";
	}

	@Override
	public String doSet(String key) {
		return "hebo";
	}

}
