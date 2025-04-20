package com.yw.ustwobacked.schedulding;

import com.yw.ustwobacked.BO.AmapWeatherResponse;
import com.yw.ustwobacked.service.TongyiService;
import com.yw.ustwobacked.service.WeatherService;
import jakarta.annotation.Resource;
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

@Component
@Slf4j
@RefreshScope
class UsTwoBotScheduler {

    @Value("${ustwo.robot.webhook-key}")
    private String usTwoHookKey;

    @Value("${ustwo.robot.enable}")
    private boolean usTwoEnable;

    @Value("${ustwo.address}")
    private List<String> addresses;

    @Value("${ustwo.protagonist}")
    private List<String> protagonists;

//    @Value("${ustwo.robot.schedule.weather.cron}")
//    private String weatherCron;

    @Resource
    private TongyiService tongyiService;

    @Resource
    private WeatherService weatherService; // 你已有的类

    private final String preText = "✨✨✨【小精灵说】✨✨✨";

    private final String tip = preText.concat("现在你是一个幽默的天气分析助手，请根据用户输入的天气信息给出穿衣建议或温馨提示。");

    private final String otherText = "请根据今天的天气，生成一段可爱的提示建议，用于给今日的主人公的日常提醒。 \n" +
            "要求如下：\n" +
            "\n" +
            "以“名字呀，”开头，比如“小颖呀，” \n" +
            "\n" +
            "内容要俏皮活泼、有趣温馨，加入一些可爱的emoji图案（避免负面情绪图案）\n" +
            "\n" +
            "不要包含穿搭建议\n" +
            "\n" +
            "根据主人公性别匹配语气（比如女生可以更甜一点，男生阳光幽默一点）\n" +
            "\n" +
            "结尾带一句积极向上的文案或小名言，形成完整闭环\n" +
            "\n" +
            "总长度控制在60字左右";

    @Scheduled(cron ="0 0 7 * * ?")
    public void sendTestMessage() {
        if (usTwoEnable) {
            sendTextMessage(usTwoHookKey);
        }
    }
    public void sendTextMessage(String webhookKey) {
        String webhookUrl = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=" + webhookKey;
        int i = 0;
        for (String address : addresses) {
            AmapWeatherResponse weather = weatherService.getWeatherByAddress(address);
            String weatherText = weatherService.getFormattedWeatherByAddress(weather);
            String sexProtagonists = i == 0
                    ? "今天的女主人公是".concat(protagonists.get(i++))
                    : "今天的男主人公是".concat(protagonists.get(1));
            String QuestionText = weatherText.concat("\n").concat(tip).concat(sexProtagonists).concat(otherText);
            String advice = tongyiService.askTongyiByWeather(QuestionText);
            String responseText = weatherText + "\n" + preText + "\n    " + advice;

            // 构造符合企业微信要求的 JSON 消息体
            String messageJson = String.format(
                    "{ \"msgtype\": \"text\", \"text\": { \"content\": \"%s\" } }",
                    responseText.replace("\"", "\\\"") // 防止特殊字符导致 JSON 格式错误
            );

            log.info("最终结果：{}", responseText);

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(webhookUrl);
                httpPost.setHeader("Content-Type", "application/json");
                httpPost.setEntity(new StringEntity(messageJson, ContentType.APPLICATION_JSON));

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                    log.info("企微发送结果：{}", responseString);
                }
            } catch (Exception e) {
                log.error("企微发送失败：{}", e.getMessage(), e);
            }
        }
    }
}