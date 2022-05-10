package com.douyasi.tutorial.blog.domain.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Article entity
 *
 * @author raoyc
 */
@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuppressWarnings("unused")
@ApiModel(value = "Article")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Article implements Serializable {

    private static final long serialVersionUID = 8648389512580360946L;

    @ApiModelProperty(value = "文章id")
    private Long id;

    @ApiModelProperty(value = "用户id", name = "user_id")
    private Long userId;

    @ApiModelProperty(value = "文章正文")
    private String content;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime, updateTime;
}
