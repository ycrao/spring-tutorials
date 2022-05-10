package com.douyasi.tutorial.blog.exception;

/**
 * ForbiddenException
 * for http status 403 error
 *
 * @author
 */
public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
