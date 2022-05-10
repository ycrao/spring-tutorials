package com.douyasi.tutorial.blog.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.douyasi.tutorial.blog.common.AuthUser;
import com.douyasi.tutorial.blog.exception.ForbiddenException;
import com.douyasi.tutorial.blog.util.ObjectUtil;
import com.douyasi.tutorial.blog.util.ValidateUtil;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.IOException;

/**
 * AuthGatewayUserArgumentResolver
 *
 * @author raoyc
 */
@Component
public class AuthGatewayUserArgumentResolver implements HandlerMethodArgumentResolver {
    
    public AuthGatewayUserArgumentResolver() {
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(AuthGatewayUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (parameter.getParameterAnnotation(AuthGatewayUser.class) instanceof AuthGatewayUser) {
            String xGatewayUserStr = webRequest.getHeader("X-Gateway-User");
            if (ValidateUtil.isNotEmpty(xGatewayUserStr)) {
                ObjectMapper mapper = ObjectUtil.getCustomizedJacksonObjectMapper();
                AuthUser authUser = null;
                try {
                    authUser = mapper.readValue(xGatewayUserStr, AuthUser.class);
                } catch (IOException e) {
                    throw new ForbiddenException("fail to authenticate access token!");
                }
                if (authUser == null) {
                    throw new ForbiddenException("illegal or incorrect credentials");
                }
                return authUser;
            }
        }
        return null;
    }
}
