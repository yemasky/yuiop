package com.utils;

import java.io.File;
import java.util.HashMap;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.CacheManagerBuilder;
import org.ehcache.PersistentCacheManager;
import org.ehcache.PersistentUserManagedCache;
import org.ehcache.UserManagedCacheBuilder;
import org.ehcache.config.CacheConfigurationBuilder;
import org.ehcache.config.ResourcePoolsBuilder;
import org.ehcache.config.persistence.CacheManagerPersistenceConfiguration;
import org.ehcache.config.persistence.DefaultPersistenceConfiguration;
import org.ehcache.config.persistence.UserManagedPersistenceContext;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.internal.persistence.DefaultLocalPersistenceService;
import org.ehcache.spi.service.LocalPersistenceService;

public class EhCache {
	private static HashMap<String, Cache<String, String>> Cache = new HashMap<String, Cache<String, String>>();
	private static CacheManager cacheManager = CacheManagerBuilder
			.newCacheManagerBuilder()
			.withCache(
					"preConfigured",
					CacheConfigurationBuilder.newCacheConfigurationBuilder()
							.buildConfig(String.class, String.class))
			.build(true);
	private static PersistentCacheManager persistentCacheManager = CacheManagerBuilder
			.newCacheManagerBuilder()
			.with(new CacheManagerPersistenceConfiguration(new File(
					"resources/", "myData")))
			.withCache(
					"persistent-cache",
					CacheConfigurationBuilder
							.newCacheConfigurationBuilder()
							.withResourcePools(
									ResourcePoolsBuilder
											.newResourcePoolsBuilder()
											.heap(10, EntryUnit.ENTRIES)
											.disk(10L, MemoryUnit.MB, true))
							.buildConfig(String.class, String.class))
			.build(true);

	public static Cache<String, String> cacheConfig(String cacheName) {
		Cache<String, String> cache = Cache.get(cacheName);
		System.out.println("aaaa:" + cache);
		if (cache == null) {
			cacheManager.getCache("preConfigured", String.class, String.class);

			cache = cacheManager.createCache(cacheName,
					CacheConfigurationBuilder.newCacheConfigurationBuilder()
							.buildConfig(String.class, String.class));
			Cache.put(cacheName, cache);
		}
		return cache;
	}

	public static void cacheUserConfig() {
		LocalPersistenceService persistenceService = new DefaultLocalPersistenceService(
				new DefaultPersistenceConfiguration(new File("resources/",
						"myUserData")));
		// persistenceService.start(, null);

		PersistentUserManagedCache<Long, String> cache = UserManagedCacheBuilder
				.newUserManagedCacheBuilder(Long.class, String.class)
				.with(new UserManagedPersistenceContext<Long, String>(
						"cache-name", persistenceService))
				.withResourcePools(
						ResourcePoolsBuilder.newResourcePoolsBuilder()
								.heap(10L, EntryUnit.ENTRIES)
								.disk(10L, MemoryUnit.MB, true)).build(true);

		// Work with the cache
		cache.put(42L, "The Answer!");
		System.out.println(cache.get(42L));
		// assertThat(cache.get(42L), is("The Answer!"));

		cache.close();
		cache.toMaintenance().destroy();
	}

	public static Cache<String, String> cacheFileConfig(String cacheName) {
		cacheName = "File" + cacheName;
		Cache<String, String> cache = Cache.get(cacheName);
		if (cache == null) {// withExpiry(expiry).
			// Cache<String, String> preConfigured =
			persistentCacheManager.getCache("persistent-cache", String.class,
					String.class);
			// Duration timeToLive = new Duration(100L, TimeUnit.DAYS);
			// Expiry<String, String> expiry =
			// Expirations.timeToLiveExpiration(timeToLive);
			cache = persistentCacheManager.createCache(cacheName,
					CacheConfigurationBuilder.newCacheConfigurationBuilder()
							.buildConfig(String.class, String.class));
			Cache.put(cacheName, cache);
			// preConfigured.put("111", "222");
			// System.out.println(preConfigured.get("111"));
		}
		return cache;
	}

	public static String getCache(String cacheName, String key) {
		return cacheConfig(cacheName).get(key);
	}

	public static boolean putCache(String cacheName, String key, String content) {
		cacheConfig(cacheName).put(key, content);
		return true;
	}

	public static String getFileCache(String cacheName, String key) {
		return cacheFileConfig(cacheName).get(key);
	}

	public static boolean putFileCache(String cacheName, String key,
			String content, int time) {
		cacheFileConfig(cacheName).put(key, content);
		return true;
	}

	public static void closeCache() {
		cacheManager.close();
		persistentCacheManager.close();
	}

}
