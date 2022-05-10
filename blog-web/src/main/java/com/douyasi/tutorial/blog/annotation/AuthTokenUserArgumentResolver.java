package com.douyasi.tutorial.blog.annotation;

import com.douyasi.tutorial.blog.common.AuthUser;
import com.douyasi.tutorial.blog.exception.ForbiddenException;
import com.douyasi.tutorial.blog.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * AuthTokenUserArgumentResolver
 *
 * @author raoyc
 */
@Component
public class AuthTokenUserArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHENTICATION_SCHEME = "Bearer";

    private final AuthService authService;
    
    @Autowired
    public AuthTokenUserArgumentResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(AuthTokenUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (parameter.getParameterAnnotation(AuthTokenUser.class) instanceof AuthTokenUser) {
            boolean guest = parameter.getParameterAnnotation(AuthTokenUser.class).guest();
            String authorizationHeader = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
            if (!isTokenBasedAuthentication(authorizationHeader)) {
                // return 403 http status
                throw new ForbiddenException("fail to authenticate access token!");
            }
            String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
            AuthUser authUser = authService.getAccessTokenUserByToken(token);
            if (authUser == null && !guest) {
                throw new ForbiddenException("illegal or incorrect credentials");
            }
            return authUser;
        }
        return null;
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }
}
