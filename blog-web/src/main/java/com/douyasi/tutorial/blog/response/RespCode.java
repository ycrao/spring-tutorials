package com.douyasi.tutorial.blog.response;

/**
 * RespCode
 *
 * @author raoyc
 */
public enum RespCode implements IMessage {

    /**
     * default: success [normal for success]
     */
    SUCCESS("20000", "ok"),

    /**
     * default: invalid [when params (like request/vo/dto...) validate fail]
     */
    INVALID("40022", "invalid params"),

    /**
     * default: fail [normal for fail when error or exception occurs]
     */
    FAIL("50000", "error or exception occurs!"),

    /*----------
    biz code start
    ----------*/

    // code from 50100 - 50199 for blog
    // if you need to handle special biz
    PASSWORD_TOO_WEAK("50101", "password is too weak"),
    EMAIL_EXISTED("50102", "email already existed!"),

    /*----------
    biz code end
    ----------*/

    /**
     * default: unknown error [others]
     */
    UNKNOWN_ERROR("99999", "system busy, try later...");




    private String code;
    private String message;

    RespCode(String errCode, String errMsg) {
        this.code = errCode;
        this.message = errMsg;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
