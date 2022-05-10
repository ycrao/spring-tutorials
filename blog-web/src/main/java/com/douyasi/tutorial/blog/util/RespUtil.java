package com.douyasi.tutorial.blog.util;

import com.douyasi.tutorial.blog.exception.AppException;
import com.douyasi.tutorial.blog.response.IMessage;
import com.douyasi.tutorial.blog.response.RespCode;
import com.douyasi.tutorial.blog.response.vo.CommonResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * RespUtil
 *
 * @author raoyc
 */
public class RespUtil {

    /**
     * response when Exception
     *
     * @param resp http servlet response
     * @param e AppException
     * @throws IOException io exception
     */
    public static void respException(HttpServletResponse resp, AppException e) throws IOException {

        if (e != null) {
            int code = Integer.parseInt(e.getCode());
            // 200 <= code < 500
            if (code >= HttpStatus.OK.value() && code < HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                resp.setStatus(code);
            } else {
                // 500
                resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        } else {
            resp.setStatus(HttpStatus.OK.value());
        }
        if (e != null) {
            resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            CommonResult<Object> result = returnError(e.getMessage(), e.getCode());
            String jsonInString = "{\"code\": \"50000\", \"message\": \"Error or exception occurs!\", \"data\": {}}";
            try {
                jsonInString = ObjectUtil.transObjectToJson(result);
            } catch (IOException ex) {
                System.out.println("transObjectToJson fail: " + ex.getStackTrace());
            }
            resp.getWriter().write(jsonInString);
        }
    }

    /**
     * return normal success result with/non-empty data
     *
     * @param data data object
     * @return result with/non-empty data
     */
    public static <T> CommonResult<T> returnResult(T data) {
        CommonResult<T> result = new CommonResult<>(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * return normal success result without/empty data
     *
     * @return result without/empty data entity
     */
    public static <T> CommonResult<T> returnResult() {
        CommonResult<T> result = new CommonResult<>(RespCode.SUCCESS.getCode(), RespCode.SUCCESS.getMessage());
        result.setData(null);
        return result;
    }

    /**
     * return error by passing code & message in
     *
     * @param msg error message
     * @param code error code
     * @return error/exception result
     */
    public static <T> CommonResult<T> returnError(String msg, String code) {
        if (ValidateUtil.isEmpty(code)) {
            code = RespCode.FAIL.getCode();
        }
        return CommonResult.fail(code, msg);
    }

    /**
     * return error using IMessage
     *
     * @param message message from IMessage
     * @return error/exception result
     */
    public static <T> CommonResult<T> returnError(IMessage message) {
        return CommonResult.fail(message);
    }

    /**
     * return invalid result when param(s) or biz validate fail
     *
     * @param msg error msg
     * @return invalid result when validate params fail
     */
    public static <T> CommonResult<T> invalidResult(String msg) {
        String code = RespCode.INVALID.getCode();
        return CommonResult.fail(code, msg);
    }
}
