package com.douyasi.tutorial.blog.annotation;

import java.lang.annotation.*;

/**
 * AuthTokenUser
 * resolve auth user by decoding redis header token key
 *
 * @author raoyc
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthTokenUser {

    /**
     * value
     *
     * @return string
     */
    String value() default "token-user";

    /**
     * guest
     *
     * open guest visit or not
     * @return boolean
     */
    boolean guest() default false;
}
