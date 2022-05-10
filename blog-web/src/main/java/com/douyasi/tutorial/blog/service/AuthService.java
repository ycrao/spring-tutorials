package com.douyasi.tutorial.blog.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.douyasi.tutorial.blog.common.AuthUser;
import com.douyasi.tutorial.blog.controller.vo.AccessTokenUser;
import com.douyasi.tutorial.blog.domain.dao.AccessTokenDao;
import com.douyasi.tutorial.blog.domain.dao.UserDao;
import com.douyasi.tutorial.blog.domain.entity.AccessToken;
import com.douyasi.tutorial.blog.domain.entity.User;
import com.douyasi.tutorial.blog.util.ObjectUtil;
import com.douyasi.tutorial.blog.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;


/**
 * AuthService
 *
 * @author raoyc
 */
@Slf4j
@Service
public class AuthService {

    private final static String ACCESS_TOKEN_CACHE_PREFIX = "access-token::";

    private final static int TOKEN_LENGTH = 40;

    private final UserDao userDao;
    
    private final AccessTokenDao accessTokenDao;

    @Qualifier("redisCacheTemplate")
    private final RedisTemplate<String, Serializable> redisCacheTemplate;

    @Autowired
    public AuthService(
            UserDao userDao,
            AccessTokenDao accessTokenDao,
            @Qualifier("redisCacheTemplate") RedisTemplate<String, Serializable> redisCacheTemplate
    ) {
        this.userDao = userDao;
        this.accessTokenDao = accessTokenDao;
        this.redisCacheTemplate = redisCacheTemplate;
    }

    /**
     * generateAccessToken
     *
     * @param user User
     * @return accessTokenUser
     */
    @Transactional(rollbackFor = Exception.class)
    public AccessTokenUser generateAccessToken(User user) {
        AccessToken accessToken = new AccessToken();
        accessToken.setUserId(user.getId());
        String token = RandomUtil.randomString(TOKEN_LENGTH);
        accessToken.setToken(token);
        Long time = System.currentTimeMillis();
        // token will expired after 12 hours from now
        Long expiredAtMillis = time + 3600*12*1000;
        Long expiredAt = expiredAtMillis/1000;
        accessToken.setExpiredAt(expiredAt);
        accessTokenDao.createAccessToken(accessToken);
        AccessTokenUser accessTokenUser = new AccessTokenUser();
        accessTokenUser.setToken(token);
        accessTokenUser.setUserId(user.getId());
        accessTokenUser.setUserName(user.getName());
        accessTokenUser.setExpiredAt(expiredAt);
        accessTokenUser.setCreateTime(user.getCreateTime());
        // handle access token to redis cache with user info
        redisCacheTemplate.opsForValue().set(ACCESS_TOKEN_CACHE_PREFIX + token, accessTokenUser, 12, TimeUnit.HOURS);
        return accessTokenUser;
    }


    /**
     * getAccessTokenUserByToken
     * to simple just get accessTokenUser from redis cache
     *
     * @param token token
     * @return accessTokenUser
     */
    public AuthUser getAccessTokenUserByToken(String token) {
        // check token length
        if (token.length() < TOKEN_LENGTH) {
            return null;
        }
        String cacheKey = ACCESS_TOKEN_CACHE_PREFIX + token;
        Serializable jsonObject = redisCacheTemplate.opsForValue().get(cacheKey);
        ObjectMapper mapper = ObjectUtil.getCustomizedJacksonObjectMapper();
        try {
            //// String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            //// AuthUser authUser = mapper.readValue(jsonStr, AuthUser.class);
            byte[] jsonByte = mapper.writeValueAsBytes(jsonObject);
            AuthUser authUser = mapper.readValue(jsonByte, AuthUser.class);
            log.debug("authUser: {}", authUser);
            return authUser;
        } catch (IOException e) {
            log.debug("exception: {}", e);
        }
        return null;
    }
}
