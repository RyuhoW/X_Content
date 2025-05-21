package com.xcontent.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        
        // キャッシュの設定
        cacheManager.setCaffeine(Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)  // 書き込みから1時間後に期限切れ
            .expireAfterAccess(2, TimeUnit.HOURS) // アクセスから2時間後に期限切れ
            .maximumSize(100)                     // 最大エントリ数
            .recordStats());                      // 統計情報の記録
        
        // キャッシュ名の設定
        cacheManager.setCacheNames(Arrays.asList(
            "reports",
            "templates",
            "analytics",
            "userProfiles"
        ));
        
        return cacheManager;
    }

    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .initialCapacity(100)
            .maximumSize(500);
    }
} 