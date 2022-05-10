package com.douyasi.tutorial.blog.response;

/**
 * IMessage
 *
 * @author raoyc
 */
public interface IMessage {
    /**
     * Error code
     *
     * @return String
     */
    String getCode();

    /**
     * Error message
     *
     * @return String
     */
    String getMessage();
}
