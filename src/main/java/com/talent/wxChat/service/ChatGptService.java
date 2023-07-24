package com.talent.wxChat.service;

import cn.hutool.json.JSONUtil;
import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.util.Proxys;
import com.talent.wxChat.config.TipsConfig;
import com.talent.wxChat.model.TextMessage;
import com.talent.wxChat.util.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author XE-CZJ
 * @Date 2023/5/13 13:47
 */
@Component
@Slf4j
public class ChatGptService {

    private static ChatGPT chatGPT;

    private static final String AI_URL = "https://api.openai.com/";

    @Value("${wall.proxy}")
    private boolean wallProxy;

    @Value("${openAi.apiKey}")
    private String apiKey;

    @Autowired
    private TipsConfig tipsConfig;



    @PostConstruct
    public void init() {
        Proxy proxy = null;
        if (wallProxy) {
            proxy = Proxys.http("127.0.0.1", 7890);
        }
        chatGPT = ChatGPT.builder()
                .apiKeyList(Arrays.asList(apiKey.split(",")))
                .timeout(3000)
                .proxy(proxy)
                .apiHost(AI_URL) //反向代理地址
                .build()
                .init();
    }


    public  Message chatCompletion(TextMessage message) {
        Message res = Message.ofAssistant("抱歉，问题太难了，出错了！");
        try {
            List<Message> messages=new ArrayList<>();
            messages.add(Message.ofSystem("You are a helpful assistant."));
            List<Message> msgHistory = CacheUtil.CHAT_RECORD_CACHE.getIfPresent(message.getFromUserName());
            if(CollectionUtils.isEmpty(msgHistory)){
                msgHistory = new ArrayList<>();
            }
            //会话上下文限制
            if (msgHistory.size() > tipsConfig.getRelatedSessions() * 2) {
                messages.addAll(msgHistory.subList(msgHistory.size() - tipsConfig.getRelatedSessions() * 2, msgHistory.size()));
            } else {
                messages.addAll(msgHistory);
            }
            messages.add(Message.of(message.getContent()));
            ChatCompletion chatCompletion = ChatCompletion.builder()
                    .model("gpt-3.5-turbo-16k-0613")
                    .messages(messages)
                    .maxTokens(6000)
                    .temperature(0.6)
                    .build();
            ChatCompletionResponse chatCompletionResponse = ChatGptService.chatGPT.chatCompletion(chatCompletion);
            res = chatCompletionResponse.getChoices().get(0).getMessage();
            msgHistory.add(Message.of(message.getContent()));
            msgHistory.add(res);
            CacheUtil.CHAT_RECORD_CACHE.put(message.getFromUserName(),msgHistory);
            log.info("执行对话结果：{}", JSONUtil.toJsonStr(res));
        } catch (Exception e) {
            log.error("请求chatGpt出错",e);
        }
        return res;
    }
}
