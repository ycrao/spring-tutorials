package com.douyasi.tutorial.blog.controller;

import com.douyasi.tutorial.blog.controller.vo.AccessTokenUser;
import com.douyasi.tutorial.blog.domain.dao.UserDao;
import com.douyasi.tutorial.blog.domain.entity.User;
import com.douyasi.tutorial.blog.exception.AppException;
import com.douyasi.tutorial.blog.request.dto.RegisterBody;
import com.douyasi.tutorial.blog.request.dto.UserCredentialsBody;
import com.douyasi.tutorial.blog.response.RespCode;
import com.douyasi.tutorial.blog.response.vo.CommonResult;
import com.douyasi.tutorial.blog.service.AuthService;
import com.douyasi.tutorial.blog.util.RespUtil;
import com.douyasi.tutorial.blog.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * UserController
 *
 * @author raoyc
 */
@RestController
@Api(value = "v1", tags = "UserApi")
public class UserController {

    private final UserDao userDao;

    private final AuthService authService;

    @Autowired
    public UserController(UserDao userDao, AuthService authService) {
        this.userDao = userDao;
        this.authService = authService;
    }

    /**
     * postRegister
     *
     * @return register result
     */
    @PostMapping("/user/register")
    @ApiOperation(value = "UserRegister", notes = "user register")
    @ApiResponses({
        @ApiResponse(
                code = 200,
                message = "UserRegister <em>Biz Exceptions Code</em>:\n"
                + "<b>50101</b> - Password is too weak\n"
                + "<b>50102</b> - Email existed"
        )
    })
    public CommonResult postRegister(@Valid @RequestBody RegisterBody registerBody, BindingResult result)
    {
        // try to validate model manual using @Valid or @Validated
        if (result.hasErrors()) {
            String message = null;
            for (FieldError fieldError : result.getFieldErrors()) {
                message = fieldError.getDefaultMessage();
                break;
            }
            return RespUtil.invalidResult(message != null ? message : "illegal or invalid params");
            // return RespUtil.invalidResult("illegal or invalid params");
        }
        String name = registerBody.getName();
        String email = registerBody.getEmail();
        String password = registerBody.getPassword();

        // TODO check email is valid or not

        // check password strength
        boolean re = StrUtil.checkPasswordStrength(password);
        if (!re) {
            throw new AppException(RespCode.PASSWORD_TOO_WEAK);
        }
        int row = 0;
        try {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setLocked(0);
            String encryptedPassword = StrUtil.encryptPassword(password, null);
            user.setPassword(encryptedPassword);
            row = userDao.createUser(user);
        } catch (Exception e) {
            // catch unique email existed insert error
            if (e instanceof DuplicateKeyException) {
                return RespUtil.returnError(RespCode.EMAIL_EXISTED);
            }
            return RespUtil.returnError(e.getMessage(), null);
        }
        if (row == 1) {
            return RespUtil.returnResult();
        } else {
            return RespUtil.returnError("insert fail!", null);
        }
    }

    /**
     * postLogin
     *
     * @return login result
     */
    @PostMapping("/user/login")
    @ApiOperation(value = "UserLogin", notes = "user login")
    public CommonResult<AccessTokenUser> postLogin(@Validated @RequestBody UserCredentialsBody credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPassword();
        String encryptedPassword = StrUtil.encryptPassword(password, null);
        User user = userDao.getUserByCredentials(email, encryptedPassword);
        if (user == null) {
            return RespUtil.invalidResult("email or password is invalid!");
        }
        AccessTokenUser accessTokenUser = authService.generateAccessToken(user);
        return RespUtil.returnResult(accessTokenUser);
    }
}
