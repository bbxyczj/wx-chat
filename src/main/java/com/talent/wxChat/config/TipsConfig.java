package com.talent.wxChat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author XE-CZJ
 * @Date 2023/7/11 10:14
 */
@Data
@Configuration
@ConfigurationProperties("wx.tips")
public class TipsConfig {

    //订阅提示语
    private String subscribeTip;

    private String chatGpt;

    private String aiYa;

    private Integer maxTimes;

    private Integer checkPeriod;

    private Integer relatedSessions;

    private String limitTip;


}
