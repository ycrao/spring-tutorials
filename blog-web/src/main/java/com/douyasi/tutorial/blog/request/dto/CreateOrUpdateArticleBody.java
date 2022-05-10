package com.douyasi.tutorial.blog.request.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * CreateOrUpdateArticleBody
 *
 * @author raoyc
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(value = "CreateOrUpdateArticleBody")
public class CreateOrUpdateArticleBody {

    @ApiModelProperty(value = "文章正文")
    @NotEmpty(message = "content cannot be empty")
    private String content;
}
