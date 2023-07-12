package com.talent.wxChat.service;

import com.plexpt.chatgpt.entity.chat.Message;
import com.talent.wxChat.config.TipsConfig;
import com.talent.wxChat.model.TextMessage;
import com.talent.wxChat.model.UserCacheBean;
import com.talent.wxChat.thread.ThisThreadPool;
import com.talent.wxChat.util.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @Author XE-CZJ
 * @Date 2023/7/10 10:43
 */
@Service
@Slf4j
public class WxChatService {


    @Autowired
    private MessageService messageService;

    @Autowired
    private TipsConfig tipsConfig;

    @Autowired
    private WxThirdService wxThirdService;

    @Autowired
    private ChatGptService chatGptService;

    //获取chatGpt结果
    public void doChat(TextMessage msg, HttpServletResponse response) {
        //事件类型
        if(msg.getMsgType().equals("event")&&msg.getEvent().equals("subscribe")){
            messageService.messageResp(msg,response,tipsConfig.getSubscribeTip());
            return;
        }
        if(!"text".equals(msg.getMsgType())){
            messageService.messageResp(msg,response,"不好意思，我当前只能识别文字消息");
            return;
        }
        //发送chatWeb地址
        if ("chatGpt" .equals(msg.getContent())) {
            messageService.messageResp(msg,response,tipsConfig.getChatGpt());
            return;
        }
        if ("哎鸭".equals(msg.getContent())) {
            messageService.messageResp(msg,response,tipsConfig.getAiYa());
            return;
        }
        //防刷限制
        boolean b = checkQuota(msg);
        if(b){
            messageService.messageResp(msg,response,tipsConfig.getLimitTip());
            return;
        }
        //推送客服输入状态
        wxThirdService.kfInputStatus(msg);
        //开线程跑chatGpt
        ThisThreadPool.threadPoolTaskExecutor.execute(() -> {
            log.info("开始异步对话ChatGpt");
            Message message = chatGptService.chatCompletion(msg);
            wxThirdService.pushKfMsg(msg,message.getContent());
        });
        messageService.messageRespSuccess(response);
        return;
    }


    private boolean checkQuota(TextMessage msg){
        UserCacheBean userCacheBean = CacheUtil.CHAT_USER_CACHE.getIfPresent(msg.getFromUserName());
        if (userCacheBean == null) {
            CacheUtil.CHAT_USER_CACHE.put(msg.getFromUserName(),UserCacheBean.builder()
                    .fromUserName(msg.getFromUserName())
                    .times(1)
                    .firstUseTime(LocalDateTime.now()).build());
            return false;
        }
        if (userCacheBean.getFirstUseTime().plusMinutes(tipsConfig.getCheckPeriod()).compareTo(LocalDateTime.now()) > 0
                && userCacheBean.getTimes() > tipsConfig.getMaxTimes()) {
            return true;
        }
        if (userCacheBean.getFirstUseTime().plusMinutes(tipsConfig.getCheckPeriod()).compareTo(LocalDateTime.now()) < 0) {
            CacheUtil.CHAT_USER_CACHE.invalidate(msg.getFromUserName());
            return false;
        }
        userCacheBean.setTimes(userCacheBean.getTimes()+1);
        return false;
    }
}
