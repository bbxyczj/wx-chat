package com.talent.wxChat.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.talent.wxChat.model.TextMessage;
import com.talent.wxChat.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author xiefc
 */
@Slf4j
@Service
public class MessageService {



    /**
     * 响应post请求，微信中消息和菜单交互都是采用post请求
     *
     * @param request
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public TextMessage messageRequest(HttpServletRequest request) {
        try {
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.xmlToMap(request);
            log.info("requestMap:"+ requestMap);
            return BeanUtil.mapToBean(requestMap,TextMessage.class,false,null);
        } catch (DocumentException e) {
            log.error(e.getMessage(),e);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }


    public void messageResp(TextMessage param,HttpServletResponse response,String txt) {
        try {
            log.info("返回微信消息 param:{}, txt:{}",param,txt);
            TextMessage newObj = new TextMessage();
            newObj.setFromUserName(param.getToUserName());
            newObj.setToUserName(param.getFromUserName());
            newObj.setContent(txt);
            newObj.setCreateTime(System.currentTimeMillis());
            String result = MessageUtil.textMessageToXml(newObj);
            response.setContentType("application/xml; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.write(result);
        } catch (IOException e) {
           log.error(e.getMessage(),e);
        }
    }

    public void messageRespSuccess(HttpServletResponse response) {
        try {
            log.info("返回微信消息 success");
            PrintWriter out = response.getWriter();
            out.write("success");
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }


}
