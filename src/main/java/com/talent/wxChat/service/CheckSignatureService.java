package com.talent.wxChat.service;

import com.talent.wxChat.model.CommonParam;
import com.talent.wxChat.util.HashUtil;
import com.talent.wxChat.config.WxConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author xiefc
 */
@Slf4j
@Service
public class CheckSignatureService {

    @Resource
    WxConfig wxConfig;

    /**
     * 实现对回传参数的hash，然后同回传参数signature比对
     *
     * @param commonParam
     * @return
     */
    public boolean checkSignature(CommonParam commonParam) {
        ArrayList<String> list = new ArrayList<>();
        //定义微信接口配置的token，同微信账号页面中配置的token值保持一致
        list.add(wxConfig.getToken());
        list.add(commonParam.getTimestamp().toString());
        list.add(commonParam.getNonce().toString());
        StringBuilder content = new StringBuilder();
        Collections.sort(list);
        for (String str : list) {
            content.append(str);
        }
        //调用hash算法，对相关参数hash
        return commonParam.getSignature().equals(HashUtil.hash(content.toString(), "SHA1"));
    }
}