package com.douyasi.tutorial.blog.domain.dao;

import javax.validation.constraints.NotNull;

import com.douyasi.tutorial.blog.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * UserDao
 *
 * @author raoyc
 */
@Mapper
@Component
@SuppressWarnings("unused")
public interface UserDao {

    /**
     * getUser
     *
     * @param id id
     * @return User
     */
    User getUser(Long id);

    /**
     * getUserByCredential
     *
     * @param email user's email
     * @param password user's encrypted password
     * @return User
     */
    User getUserByCredentials(
        @NotNull @Param("email") String email,
        @NotNull @Param("password") String password
    );

    /**
     * createUser
     *
     * @param user user entity
     * @return int
     */
    int createUser(
        @Param("user") User user
    );
}
