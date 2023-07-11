package com.talent.wxChat.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.talent.wxChat.config.WxConfig;
import com.talent.wxChat.model.InputStatusMessage;
import com.talent.wxChat.model.KfMessage;
import com.talent.wxChat.model.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.talent.wxChat.util.CacheUtil.ACCESS_TOKEN_CACHE;

/**
 * @Author XE-CZJ
 * @Date 2023/7/11 11:04
 */
@Service
@Slf4j
public class WxThirdService {

    @Autowired
    private WxConfig wxConfig;

    private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    private static final String KF_MSG_URL="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

    private static final String INPUT_STATUS_URL="https://api.weixin.qq.com/cgi-bin/message/custom/typing?access_token=ACCESS_TOKEN";


    /**
     * 推送客服消息
     * @param msg
     * @param content
     */
    public void pushKfMsg(TextMessage msg,String content) {
        KfMessage kfMessage = KfMessage.builder()
                .touser(msg.getFromUserName())
                .msgtype("text")
                .text(KfMessage.builder().content(content).build()).build();
        try {
            log.info("推送客服消息入参{}",kfMessage);
            String result = HttpUtil.post(KF_MSG_URL.replace("ACCESS_TOKEN", getAccessToken()),
                    JSONUtil.toJsonStr(kfMessage));
            log.info("推送客服消息成功：{}",result);
        } catch (Exception e) {
            log.error("推送客服消息失败",e);
        }
    }

    public void kfInputStatus(TextMessage msg){
        InputStatusMessage inputStatusMessage = InputStatusMessage.builder()
                .touser(msg.getFromUserName())
                .command("Typing").build();
        try {
            log.info("推送客服输入状态入参{}",inputStatusMessage);
            String result = HttpUtil.post(INPUT_STATUS_URL.replace("ACCESS_TOKEN", getAccessToken()),
                    JSONUtil.toJsonStr(inputStatusMessage));
            log.info("推送客服输入状态成功：{}",result);
        } catch (Exception e) {
            log.error("推送客服输入状态失败",e);
        }
    }

    public String getAccessToken(){
        String accessToken = ACCESS_TOKEN_CACHE.getIfPresent(wxConfig.getAppId());
        if(StringUtils.isNotBlank(accessToken)){
            return accessToken;
        }
        String result = HttpUtil.get(ACCESS_TOKEN_URL.replace("APPID", wxConfig.getAppId())
                .replace("APPSECRET", wxConfig.getSecret()));
        log.info("getAccessToken result:{}",result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        accessToken = jsonObject.getString("access_token");
        ACCESS_TOKEN_CACHE.put(wxConfig.getAppId(),accessToken);
        return accessToken;
    }
}
