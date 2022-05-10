package com.douyasi.tutorial.blog.response.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * BaseResult
 * 
 * @author raoyc
 */
@ApiModel(value = "BaseResult")
public abstract class BaseResult<T> implements Serializable {

    private static final long serialVersionUID = 1489958255058093796L;

    /**
     * error code
     */
    @ApiModelProperty(example = "\"20000\"", value = "code: 20000 为正常结果; 40022 为参数校验错误; 大于 50000 为异常或业务逻辑 code")
    private String code;

    /**
     * message to response
     */
    @ApiModelProperty(value = "message: 如果存在异常时，会返回错误信息")
    private String message;

    /**
     * data to response
     */
    @ApiModelProperty(value = "data: 实体对象")
    private T data;

    /**
     * Current time stamp
     */
    @ApiModelProperty(value = "timestamp", notes = "当前时间", position = 3)
    private String timestamp;

    /**
     * getCode
     * 
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * setCode
     * 
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * getMessage
     * 
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * setMessage
     * 
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * getData
     * 
     * @return
     */
    public T getData() {
        return data;
    }

    /**
     * setData
     * 
     * @param data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * getTimestamp
     * 
     * @return
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * setTimestamp
     * 
     * @param timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
