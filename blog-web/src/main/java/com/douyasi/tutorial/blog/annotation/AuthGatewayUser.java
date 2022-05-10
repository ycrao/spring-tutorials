package com.douyasi.tutorial.blog.annotation;

import java.lang.annotation.*;

/**
 * AuthGatewayUser
 * resolve auth user by decoding x-gateway-user header
 * using in Kong/OpenResty/apisix api-gateway etc.
 *
 * @author raoyc
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthGatewayUser {
    String value() default "gateway-user";
}
