package com.talent.wxChat.model;

import lombok.Data;

/**
 * @author xiefc
 */
@Data
public class TextMessage {


    private String MsgId;

    /**
     * 开发者微信号
     */
    private String ToUserName;

    /**
     * 发送方帐号（一个OpenID）
     */
    private String FromUserName;

    /**
     * 创建时间
     */
    private long CreateTime;


    private String Event;


    /**
     * 消息类型
     * @ApiModelProperty(value = "消息类型", hidden = true)
     */
    private String MsgType = "text";

    /**
     * 消息内容
     */
    private String Content;
}

