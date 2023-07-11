package com.talent.wxChat.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author XE-CZJ
 * @Date 2023/7/11 15:42
 */
@Data
@Builder
public class UserCacheBean implements Serializable {
    private static final long serialVersionUID = -8937065502995063854L;
    private String fromUserName;

    private LocalDateTime firstUseTime;

    private Integer times;
}
