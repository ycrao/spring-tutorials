# readme

### using redis or cache (by redis)

When using `@CachePut` annotation we will get redis cache like below:

- key: `accessToken::ivd4eIPeWFZnPegxrQDnsKWjFYqPaVzSKVVc8m90`
- value: see below

```json
{
  "@class":"com.douyasi.tutorial.blog.controller.vo.AccessTokenUser",
  "token":"ivd4eIPeWFZnPegxrQDnsKWjFYqPaVzSKVVc8m90",
  "userName":"wangwu",
  "userId":9,
  "expiredAt":1650915693,
  "createTime":[
    "java.util.Date",
    "2022-04-25 13:44:29"
  ]
}
```

`CachePut` code like these:

```java
package com.douyasi.tutorial.blog.service;

import com.douyasi.tutorial.blog.controller.vo.AccessTokenUser;
import com.douyasi.tutorial.blog.domain.dao.AccessTokenDao;
import com.douyasi.tutorial.blog.domain.dao.UserDao;
import com.douyasi.tutorial.blog.domain.entity.AccessToken;
import com.douyasi.tutorial.blog.domain.entity.User;
import com.douyasi.tutorial.blog.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * AuthService
 *
 * @author raoyc
 */
@Service
public class AuthService {

    private final UserDao userDao;

    private final AccessTokenDao accessTokenDao;

    @Autowired
    public AuthService(UserDao userDao, AccessTokenDao accessTokenDao) {
        this.userDao = userDao;
        this.accessTokenDao = accessTokenDao;
    }
    
    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "accessToken", key = "#result.token")
    public AccessTokenUser generateAccessToken(User user) {
        AccessToken accessToken = new AccessToken();
        accessToken.setUserId(user.getId());
        String token = RandomUtil.randomString(40);
        accessToken.setToken(token);
        Long time = System.currentTimeMillis();
        // token will expired after 12 hours from now
        Long expiredAtMillis = time + 3600*12*1000;
        Long expiredAt = expiredAtMillis/1000;
        accessToken.setExpiredAt(expiredAt);
        accessTokenDao.createAccessToken(accessToken);
        AccessTokenUser accessTokenUser = new AccessTokenUser(token, user.getName(), user.getId(), expiredAt, user.getCreateTime());
        // TODO handle access token to redis cache with user info
        return accessTokenUser;
    }
}

```

If you just want some pure json string, need using redisTemplate to manual handle it.

Example like these:

```java
package com.douyasi.tutorial.blog.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.douyasi.tutorial.blog.controller.vo.AccessTokenUser;
import com.douyasi.tutorial.blog.domain.dao.AccessTokenDao;
import com.douyasi.tutorial.blog.domain.dao.UserDao;
import com.douyasi.tutorial.blog.domain.entity.AccessToken;
import com.douyasi.tutorial.blog.domain.entity.User;
import com.douyasi.tutorial.blog.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * AuthService
 *
 * @author raoyc
 */
@Slf4j
@Service
public class AuthService {

    private final UserDao userDao;

    private final AccessTokenDao accessTokenDao;

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public AuthService(UserDao userDao, AccessTokenDao accessTokenDao, StringRedisTemplate stringRedisTemplate) {
        this.userDao = userDao;
        this.accessTokenDao = accessTokenDao;
        this.stringRedisTemplate = stringRedisTemplate;
    }
    
    /**
     * generateAccessToken
     * @param user User
     * @return accessTokenUser
     */
    @Transactional(rollbackFor = Exception.class)
    public AccessTokenUser generateAccessToken(User user) {
        AccessToken accessToken = new AccessToken();
        accessToken.setUserId(user.getId());
        String token = RandomUtil.randomString(40);
        accessToken.setToken(token);
        Long time = System.currentTimeMillis();
        // token will expired after 12 hours from now
        Long expiredAtMillis = time + 3600*12*1000;
        Long expiredAt = expiredAtMillis/1000;
        accessToken.setExpiredAt(expiredAt);
        accessTokenDao.createAccessToken(accessToken);
        AccessTokenUser accessTokenUser = new AccessTokenUser(token, user.getName(), user.getId(), expiredAt, user.getCreateTime());
        // handle access token to redis cache with user info
        try {
            stringRedisTemplate.opsForValue().set("access-token::" + token, transAccessTokenUserToJson(accessTokenUser), 12, TimeUnit.HOURS);
        } catch (IOException e) {
            log.debug("transAccessTokenUserToJson exception: {}", e);
        }
        return accessTokenUser;
    }

    /**
     * transAccessTokenUserToJson
     *
     * @param accessTokenUser access token user
     * @return json string
     * @throws JsonProcessingException
     */
    private String transAccessTokenUserToJson(AccessTokenUser accessTokenUser) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonString = mapper.writeValueAsString(accessTokenUser);
        return jsonString;
    }
}
```