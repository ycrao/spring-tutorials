package com.douyasi.tutorial.blog.request.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * RegisterBody
 *
 * @author raoyc
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(value = "RegisterBody")
public class RegisterBody {

    @ApiModelProperty(value = "昵称|name")
    @NotEmpty(message = "name cannot be empty")
    private String name;

    @ApiModelProperty(value = "电子邮箱地址|email")
    @NotEmpty(message = "email cannot be empty")
    private String email;

    @ApiModelProperty("密码|password")
    @NotEmpty(message = "password cannot be empty")
    private String password;
}
