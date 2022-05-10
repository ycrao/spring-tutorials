package com.douyasi.tutorial.blog.controller;

import com.douyasi.tutorial.blog.exception.AppException;
import com.douyasi.tutorial.blog.response.vo.CommonResult;
import com.douyasi.tutorial.blog.util.RespUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ApiController
 *
 * @author raoyc
 */
@RestController
@Api(value = "v1", tags = "CommonApi")
public class ApiController {

    /**
     * index test
     */
    @GetMapping("/")
    public CommonResult<?> getIndex() {
      return RespUtil.returnResult();
    }

    /**
     * error test
     */
    @GetMapping("/err")
    public void getError() {
        throw new AppException("50000", "error by app exception");
    }

    /**
     * ping test
     */
    @GetMapping(value = "/ping", produces = "text/plain")
    public String getPing() {
        return "pong";
    }

    /**
     * health check
     */
    @GetMapping(value="/health", produces = "text/plain")
    @ApiOperation(value = "health check", notes = "only for health check")
    public String getHealth() {
        return "ok";
    }
}