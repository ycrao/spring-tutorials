package com.douyasi.tutorial.blog.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * AccessTokenUser
 *
 * @author raoyc
 */
@NoArgsConstructor
@Data
@ApiModel(value = "AccessTokenUser")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccessTokenUser implements Serializable {

    private static final long serialVersionUID = 7401598100093480515L;

    @ApiModelProperty(value = "Token")
    private String token;

    @ApiModelProperty(value = "UserName", name = "user_name")
    private String userName;

    @ApiModelProperty(value = "UserId", name = "user_id")
    private Long userId;

    @ApiModelProperty(value = "TokenExpiredAt", name = "expired_at")
    private Long expiredAt;

    @ApiModelProperty(value = "UserCreateTime", name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
