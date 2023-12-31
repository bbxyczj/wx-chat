# 微信公众号（服务号）接入chatGPT

> 首先需要注册open AI并生成key，参考教程[openAi](https://platform.openai.com/)

## 使用方法

**下载代码**

```bash
git clone https://github.com/bbxyczj/wx-chat.git
```

**微信配置**

#### 1.配置你服务器的ip白名单
![1](file/20230714090421.png)

#### 1.启用服务器配置，指定token和服务器域名

![1](file/20230714090430.png)


**更新配置**

配置 | 来源 |	描述|
---|---|---|
appId|微信公众平台|![1](file/20230714090421.png)|
secret|微信公众平台|![1](file/20230714090421.png)|
token|微信公众平台|![1](file/20230714090430.png)|
subscribeTip|关注提示语|自定义|
chatGpt|指定输入提示语|自定义|
aiYa|指定输入提示语|自定义|
openAi.apiKey|openAI api key|[openAi](https://platform.openai.com/)|


**运行服务**

```bash
java -jar wx-chat-0.1.1.jar 
```


## 特性

同步返回微信`success`,通过微信客服消息推送ChatGpt返回消息



## 使用体验
![1](file/640.png)

[我们接入AI对话功能啦](https://mp.weixin.qq.com/s/PKHmMYQh7uUsv_GtR4UOwg)


## 一些实现说明

### chatGpt实现
[plex大佬的项目](https://github.com/PlexPt)
[ChatGPT Java API](https://github.com/PlexPt/chatgpt-java.git)



## 体验

扫码关注服务号开始体验～

![1](file/b730498483385daf7fc9e33bd50a16d.jpg)

## 另有小程序接入体验（支持AI绘图）
![2](file/20230620192517.png)


![2](file/gh_6ddb53f523a6_430.jpg)
