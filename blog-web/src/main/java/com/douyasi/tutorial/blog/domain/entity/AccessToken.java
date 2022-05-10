package com.douyasi.tutorial.blog.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * AccessToken entity
 *
 * @author raoyc
 */
@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuppressWarnings("unused")
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 8199502245994778557L;

    private Long id, userId;

    private String token;

    private Long expiredAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime, updateTime;
}
