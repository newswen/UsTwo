package com.yw.ustwobacked.schedulding;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * 说明
 *
 * @author: yuanwen
 * @since: 2025/4/16
 */
@Component
@Slf4j
@RefreshScope
public class RemindXiaobaoSendPhotoScheDulding {

    @Value("${ustwo.robot.webhook-key}")
    private String usTwoHookKey;

    private static final List<String> cuteLines = List.of(
            "人家都等不及啦～快点发照片咯！🥺",
            "小宝发张今日份的靓照叭，想你咯～🧸",
            "不发照片我要生气气啦！哼~🐰",
            "照片一发，开心加倍～快来~🎀",
            "每天一张小宝照，是我幸福的源泉~💕",
            "没有照片我怎么能上班得着嘛～🌙",
            "今天的小宝一定又超好看！快给我康康👀",
            "宝宝再不发照片我就要贴贴惹～😚",
            "我已经摆好姿势准备接图啦📸",
            "快来点亮我今天的心情吧～✨"
    );


    @Scheduled(cron ="0 0 13 * * ?")
    public void remindXiaobaoSendPhoto() {
        String content = buildCuteReminderText();
        sendReminderMessage(usTwoHookKey, content);
    }

    private String buildCuteReminderText() {
        String prefix = "📸 小宝呀，该发今天的照片啦！";
        String randomCute = cuteLines.get(new Random().nextInt(cuteLines.size()));
        return prefix + "\n" + randomCute;
    }


    private void sendReminderMessage(String webhookKey, String content) {
        String webhookUrl = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=" + webhookKey;
        String messageJson = String.format(
                "{ \"msgtype\": \"text\", \"text\": { \"content\": \"%s\" } }",
                content.replace("\"", "\\\"")
        );

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(webhookUrl);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(messageJson, ContentType.APPLICATION_JSON));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                log.info("提醒小宝发照片发送结果：{}", responseString);
            }
        } catch (Exception e) {
            log.error("提醒小宝发照片失败：{}", e.getMessage(), e);
        }
    }


}
