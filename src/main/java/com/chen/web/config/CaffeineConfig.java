package com.chen.web.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021-02-18-2:18
 */
@Configuration
public class CaffeineConfig {

    /*@Autowired
    private ChainApiService chainApiService;

    @Bean("blockCacheManager")
    public CacheManager blockCacheManager() {

        *//*LoadingCache<Object, Block> build = Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .maximumSize(1000)
                .build(key -> chainApiService.getBlockById2(String.valueOf(key)));*//*
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .maximumSize(100)
            .initialCapacity(50));
        // caffeineCacheManager.setCacheLoader(key -> chainApiService.getBlockById2(String.valueOf(key)));
        return caffeineCacheManager;
    }

    @Bean
    @Primary
    public CacheManager cacheManager() {

        *//*LoadingCache<Object, Block> build = Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .maximumSize(1000)
                .build(key -> chainApiService.getBlockById2(String.valueOf(key)));*//*
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .maximumSize(500)
                .initialCapacity(50));
        return caffeineCacheManager;
    }

    @Bean
    public CacheManager chainInfoManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(500, TimeUnit.MILLISECONDS)
                .maximumSize(100)
                .initialCapacity(50));
        return caffeineCacheManager;
    }*/

    @Bean
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        Cache<Object, Object> blockCache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .maximumSize(100)
                .initialCapacity(50)
                .build();
        Cache<Object, Object> chainInfoCache = Caffeine.newBuilder()
                .expireAfterWrite(500, TimeUnit.MILLISECONDS)
                .maximumSize(100)
                .initialCapacity(50)
                .build();
        simpleCacheManager.setCaches(Arrays.asList(new CaffeineCache("blockCache", blockCache)
                , new CaffeineCache("chainInfoCache", chainInfoCache)));
        return simpleCacheManager;
    }
}
