package com.douyasi.tutorial.blog.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * CacheConfig
 *
 * @author raoyc
 */
@Configuration
@EnableCaching
public class CacheConfig {
    

    /**
     * cacheManager
     * by redis
     *
     * @param factory redis connection factory
     * @return redis cache manager
     */
    @Bean("cacheByRedis")
    public CacheManager cacheManager(RedisConnectionFactory factory) {

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();

        RedisSerializationContext.SerializationPair stringPair = RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());

        RedisSerializationContext.SerializationPair genericJsonPair = RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());

        RedisCacheConfiguration redisCacheConfiguration = config
                // setting default ttl: 2 hours
                .entryTtl(Duration.ofHours(2))
                .serializeKeysWith(stringPair)
                .serializeValuesWith(genericJsonPair);

        return RedisCacheManager.builder(factory).cacheDefaults(redisCacheConfiguration).build();
    }
}
