package com.douyasi.tutorial.blog.exception;


import com.douyasi.tutorial.blog.util.RespUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;


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
    * handle AppException and rewrite http status code
    *
    * @param ex AppException
    * @return response entity
    */
    @ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAppException(AppException ex) {
        // AppException always using 200 http status code for handling business logic when calling
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(RespUtil.returnError(ex.getMessage(), ex.getCode()), status);
    }

    /**
     * handle ForbiddenException and rewrite http status code
     *
     * @param ex ForbiddenException
     * @return response entity
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException ex) {
        // AppException always using 200 http status code for handling business logic when calling
        HttpStatus status = HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(RespUtil.returnError(ex.getMessage(), null), status);
    }

    /**
     * handle UnauthorizedException and rewrite http status code
     *
     * @param ex UnauthorizedException
     * @return response entity
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        // AppException always using 200 http status code for handling business logic when calling
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return new ResponseEntity<>(RespUtil.returnError(ex.getMessage(), null), status);
    }

    /**
     * rewrite/override MethodArgumentNotValidException method for `javax.validation.constraints.*`
     *
     * @param ex MethodArgumentNotValidException
     * @param headers headers
     * @param status original status
     * @param request request
     * @return response entity
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        HttpStatus okStatus = HttpStatus.OK;
        return new ResponseEntity<>(RespUtil.invalidResult(errorMessage != null ? errorMessage : "illegal or invalid params"), okStatus);
    }
}
