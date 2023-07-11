package com.talent.wxChat.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author XE-CZJ
 * @Date 2023/7/11 11:35
 */
@Data
@Builder
public class KfMessage implements Serializable {
    private static final long serialVersionUID = 3961172716700566857L;

    private String touser;

    private String msgtype;

    private KfMessage text;

    private String content;



}
