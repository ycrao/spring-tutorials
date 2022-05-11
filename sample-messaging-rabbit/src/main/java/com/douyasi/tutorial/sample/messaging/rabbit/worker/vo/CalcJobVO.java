package com.douyasi.tutorial.sample.messaging.rabbit.worker.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * CalcJobVO
 *
 * @author raoyc
 */
@Data
@ToString
public class CalcJobVO implements Serializable {

    private static final long serialVersionUID = 5487388021403779946L;
    
    private String op;

    private Integer a;

    private Integer b;
}
