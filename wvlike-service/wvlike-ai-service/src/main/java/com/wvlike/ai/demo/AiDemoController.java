package com.wvlike.ai.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: xinwen_tu
 * @Date: 2026/2/3
 * Description:
 */
@RestController(value = "/wvlike/ai")
public class AiDemoController {

    @Resource
    private ChatClient chatClient;

    @GetMapping("/generation")
    String generation(String userInput) {
        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }

}
