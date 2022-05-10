package com.douyasi.tutorial.blog.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * User entity
 *
 * @author raoyc
 */
@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuppressWarnings("unused")
public class User implements Serializable {

    private static final long serialVersionUID = 1273550144492903917L;
    
    private Long id;

    private int locked;

    private String name, email, password;
    
    public void setLocked(int locked) {
        this.locked = (locked >= 1) ? 1 : 0;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime, updateTime;

}
