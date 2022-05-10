package com.douyasi.tutorial.blog.request.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * UserCredentialsBody for `/user/login` api
 *
 * @author raoyc
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(value = "UserCredentialsBody")
public class UserCredentialsBody {

    @ApiModelProperty(value = "电子邮箱地址|email")
    @NotEmpty(message = "email cannot be empty")
    private String email;

    @ApiModelProperty(value = "密码|password")
    @NotEmpty(message = "password cannot be empty")
    private String password;
}
