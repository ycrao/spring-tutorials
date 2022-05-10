package com.douyasi.tutorial.blog.exception;

import com.douyasi.tutorial.blog.response.IMessage;

/**
 * AppException
 *
 * @author raoyc
 */
public class AppException extends RuntimeException {

    private String code;
    private String message;

    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
        this.message = message;
    }

    public AppException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public AppException(IMessage iMessage) {
        this.code = iMessage.getCode();
        this.message = iMessage.getMessage();
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
