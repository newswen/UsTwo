package com.yw.ustwobacked.service;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class TongyiService {

    @Value("${tongyi.api.key}")
    private String apiKey;

    /**
     * 根据天气摘要向通义模型提问
     */
    public String askTongyiByWeather(String weatherSummary) {
        Generation gen = new Generation();

        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("现在你是一个幽默的天气分析助手，请根据用户输入的天气信息给出穿衣建议或温馨提示。")
                .build();

        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(weatherSummary)
                .build();

        GenerationParam param = GenerationParam.builder()
                .apiKey(apiKey)
                .model("qwen-max")
                .messages(Arrays.asList(systemMsg, userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .temperature(0.8f)
                .build();

        try {
            GenerationResult result = gen.call(param);
            return result.getOutput().getChoices().get(0).getMessage().getContent();
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            log.error("通义请求出错：{}", e.getMessage(), e);
            return "调用通义服务失败，请稍后再试。";
        }
    }
}
