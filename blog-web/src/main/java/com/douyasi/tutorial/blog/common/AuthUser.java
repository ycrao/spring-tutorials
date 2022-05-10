package com.douyasi.tutorial.blog.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * AuthUser
 * copy of AccessTokenUser, for authorization
 *
 * @author raoyc
 */
@NoArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthUser implements Serializable {

    private static final long serialVersionUID = 7199504466602257609L;

    @ApiModelProperty(hidden = true)
    private String token;

    @ApiModelProperty(hidden = true)
    private String userName;

    @ApiModelProperty(hidden = true)
    private Long userId;

    @ApiModelProperty(hidden = true)
    private Long expiredAt;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
