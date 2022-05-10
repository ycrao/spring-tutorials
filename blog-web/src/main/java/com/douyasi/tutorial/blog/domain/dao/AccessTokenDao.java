package com.douyasi.tutorial.blog.domain.dao;

import com.douyasi.tutorial.blog.domain.entity.AccessToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * AccessTokenDao
 *
 * @author raoyc
 */
@Mapper
@Component
@SuppressWarnings("unused")
public interface AccessTokenDao {

    /**
     * getAccessTokenByToken
     *
     * @param token access token from request header
     * @return AccessToken
     */
    AccessToken getAccessTokenByToken(
        @NotNull @NotEmpty @Param("token") String token
    );

    /**
     * createAccessToken
     * 
     * @param accessToken accessToken
     * @return int
     */
    int createAccessToken(
        @NotNull @Param("accessToken") AccessToken accessToken
    );
}
