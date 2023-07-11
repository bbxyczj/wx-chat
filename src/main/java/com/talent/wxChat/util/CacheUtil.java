package com.talent.wxChat.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.plexpt.chatgpt.entity.chat.Message;
import com.talent.wxChat.model.UserCacheBean;

import java.time.Duration;
import java.util.List;

/**
 * @Author XE-CZJ
 * @Date 2023/7/11 11:15
 */
public class CacheUtil {

    public static final Cache<String,String> ACCESS_TOKEN_CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(Duration.ofSeconds(7100))
            .build();


    public static final Cache<String, List<Message>> CHAT_RECORD_CACHE = CacheBuilder.newBuilder()
            .maximumSize(2000)
            .expireAfterAccess(Duration.ofHours(12))
            .build();

    public static final Cache<String, UserCacheBean> CHAT_USER_CACHE = CacheBuilder.newBuilder()
            .expireAfterAccess(Duration.ofHours(6))
            .build();
}
