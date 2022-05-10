# readme


### GlobalExceptionHandler

Spring boot framework default error json output like blow ( `404 not found` error for example):

```json
{
    "timestamp": "2022-04-24T12:28:33.552+00:00",
    "status": 404,
    "error": "Not Found",
    "path": "/api/t-blog/404"
}
```

When throw a user-defined exception in controller or service, such as AppException, we need to write code handle these. here is some code you can ref:



```java
package com.douyasi.tutorial.blog.exception;


import com.douyasi.tutorial.blog.util.RespUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * GlobalExceptionHandler
 * notice: only handle 500 Exception and AppException other exceptions such
 * as 404 not found/ 405 method not allowed / 415 unsupported media type etc.
 *
 * @author raoyc
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Override Exception 
     * it keeps  http status code as original,
     * but modify response body using CommonResult as ResponseEntity.
     *
     * @param ex the exception
     * @param body the body for the response
     * @param headers the headers for the response
     * @param status the response status
     * @param request the current request
     * @return response entity
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
        Object body,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {
        return new ResponseEntity<>(RespUtil.returnError(ex.getMessage(), ""), status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer code = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus status = HttpStatus.resolve(code);
        return (status != null) ? status : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * handle AppException and rewrite http status code
     *
     * @param request request
     * @param ex AppException
     * @return response entity
     */
    @ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAppException(HttpServletRequest request, AppException ex) {
        // AppException always using 200 http status code for handling business logic when calling
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(RespUtil.returnError(ex.getMessage(), ex.getCode()), status);
    }


}
```

Maybe you can ref official document: [spring-mvc:error-handling](https://docs.spring.io/spring-boot/docs/2.6.7/reference/htmlsingle/#web.servlet.spring-mvc.error-handling) .