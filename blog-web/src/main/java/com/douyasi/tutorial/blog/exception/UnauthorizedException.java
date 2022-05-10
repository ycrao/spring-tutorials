package com.douyasi.tutorial.blog.exception;

/**
 * UnauthorizedException
 * for http status 401 error
 *
 * @author raoyc
 */
public class UnauthorizedException extends RuntimeException {
    
    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
