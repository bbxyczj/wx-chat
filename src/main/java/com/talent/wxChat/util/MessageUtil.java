package com.talent.wxChat.util;

import cn.hutool.core.io.BOMInputStream;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.talent.wxChat.model.TextMessage;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 消息处理工具类
 * @author xiefc
 */
@Slf4j
public class MessageUtil {
    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";

    public static final String EVENT_SUB = "subscribe";
    public static final String EVENT_UNSUB = "unsubscribe";
    public static final String EVENT_CLICK = "CLICK";
    public static final String EVENT_VIEW = "VIEW";

    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
    /**
     * xml转为map
     * @param request
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws DocumentException, IOException
    {
        Map<String,String> map = new HashMap<String, String>(3);
        try {
            SAXReader reader = new SAXReader();
            InputStream in = request.getInputStream();
            log.info("request body:{}",in);
            BOMInputStream bomInputStream = new BOMInputStream(in,"UTF-8");
            //转成xml
            Document doc = reader.read(bomInputStream);

            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
            bomInputStream.close();
        } catch (Exception e) {
          log.error("xmlToMap",e);
        } finally {

        }
        return map;
    }
    /**
     * 解析reqString中xml格式消息
     * @param reqString HttpServletRequest
     * @return Map<节点>
     */
    public static Map<String,String> string2Map(String reqString) {
        try {
            String xml = reqString;
            Map<String,String> maps = new HashMap<>(3);
            Document document = DocumentHelper.parseText(xml);
            System.out.println(document.asXML());
            Element root = document.getRootElement();
            List<Element> eles = root.elements();
            for (Element e:eles){
                maps.put(e.getName(),e.getTextTrim());
            }
            return maps;
        }catch (DocumentException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String textMessageToXml(TextMessage textMessage){
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);

    }



    public static String initText(String toUserName, String fromUserName, String content){
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MESSAGE_TEXT);
        text.setCreateTime(System.currentTimeMillis());
        text.setContent(content);
        return textMessageToXml(text);
    }
}

