package com.talent.wxChat.controller;

import cn.hutool.core.util.StrUtil;
import com.talent.wxChat.model.CommonParam;
import com.talent.wxChat.model.TextMessage;
import com.talent.wxChat.service.CheckSignatureService;
import com.talent.wxChat.service.MessageService;
import com.talent.wxChat.service.WxChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author XE-CZJ
 * @Date 2023/7/10 10:20
 */
@RestController
@Slf4j
public class WxChatController {

    @Autowired
    private WxChatService wxChatService;

    @Autowired
    private CheckSignatureService checkSignatureService;

    @Autowired
    private MessageService messageService;

    @GetMapping("wxChat")
    public Object checkParam(CommonParam commonParam){
        boolean b = checkSignatureService.checkSignature(commonParam);
        if(b&&StrUtil.isNotBlank(commonParam.getEchostr())){
            return commonParam.getEchostr();
        }
        return "";
    }

    @PostMapping("wxChat")
    public void chat(CommonParam commonParam, HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean b = checkSignatureService.checkSignature(commonParam);
        //解析入参
        if(b){
            TextMessage textMessage = messageService.messageRequest(request);
            //处理请求
            wxChatService.doChat(textMessage,response);
        }else {
            response.getWriter().println("");
        }
    }
}
