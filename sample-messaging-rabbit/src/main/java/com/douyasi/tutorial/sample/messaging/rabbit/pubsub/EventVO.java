package com.douyasi.tutorial.sample.messaging.rabbit.pubsub;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * EventVO
 * data for events
 *
 * @author raoyc
 */
@Data
@Accessors(chain = true)
@ToString
public class EventVO implements Serializable {

    private static final long serialVersionUID = -1161287678847017004L;

    public final static String EVENT_TYPE_LOGIN = "login";
    public final static String EVENT_TYPE_ADD_TO_CART = "add-to-cart";
    public final static String EVENT_TYPE_CHECKOUT = "checkout";
    public final static String EVENT_TYPE_PAY = "pay";
    public final static String EVENT_TYPE_LOGOUT = "logout";

    /**
     * uuid of event
     */
    private String uuid;

    /**
     * type of event
     */
    private String type;

    /**
     * related userId
     */
    private Long userId;

    /**
     * event's body
     */
    private String body;
}
