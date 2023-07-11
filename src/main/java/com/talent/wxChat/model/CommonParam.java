package com.talent.wxChat.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author XE-CZJ
 * @Date 2023/7/10 10:47
 */
@Data
public class CommonParam implements Serializable {
    private static final long serialVersionUID = -680327716624135193L;

    private String signature;

    private Long timestamp;

    private Long nonce;

    private String echostr;

    private String openid;
}
