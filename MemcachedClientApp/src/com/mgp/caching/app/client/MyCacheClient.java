package com.mgp.caching.app.client;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.DefaultConnectionFactory;
import net.spy.memcached.MemcachedClient;

public class MyCacheClient {

//	private static final ResourceBundle BUNDLE = ResourceBundle
//			.getBundle("memcached.properties");

	private static MemcachedClient memcachedClient;

	/**
	 * check whether the data is cacheable
	 * 
	 * */

	// public<T> boolean isCacheable(Class<T> clazz) {
	// Boolean cacheable = false;
	// if(this.)
	// }

	/**
	 * check the cleint is connected
	 */
	public synchronized boolean isConnected() {
		return MyCacheClient.memcachedClient != null;
	}

	public final void setDataInMemcached(Object obj) {
		memcachedClient.set("KEY", 4000, obj.toString());

	}

	public <T> T getdataFromMemcached(String id, Class<T> c) {
		Object obj = null;

		obj = memcachedClient.get(id);
		return c.cast(obj);
	}

	/**
	 * @param args
	 *            Implementation of the memcached iinteraction is here
	 */
	public static void main(String[] args) {

		try {
			memcachedClient = new MemcachedClient(
					new DefaultConnectionFactory(),
					AddrUtil.getAddresses("localhost:11211"));
			if (memcachedClient != null) {
				Thread.sleep(100);
				for (int i = 0; memcachedClient.getAvailableServers().size() == 0
						&& i < 10; i++) {
					Thread.sleep(100);
				}
			}
			MyCacheClient cacheClient = new MyCacheClient();
			cacheClient.setDataInMemcached("Pradeepa");
			Thread.sleep(10100000);
			memcachedClient.shutdown();
			System.out.println("Wait for some time to see the result");
//			while (Thread.currentThread().isAlive()) {
//				System.out.print(".");
//
//			}
			System.out.println(cacheClient.getdataFromMemcached("KEY", String.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
