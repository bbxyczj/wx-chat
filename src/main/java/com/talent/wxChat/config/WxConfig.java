package com.talent.wxChat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiefc
 */
@Data
@Configuration
@ConfigurationProperties("wx.mp")
public class WxConfig {
    /**
     * 微信token
     */
    public String token;

    public String secret;

    public String appId;

}