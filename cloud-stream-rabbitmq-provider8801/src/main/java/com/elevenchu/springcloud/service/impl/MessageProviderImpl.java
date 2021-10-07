package com.elevenchu.springcloud.service.impl;

import com.elevenchu.springcloud.service.IMessageProvider;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

@EnableBinding(Source.class)//定义消息的推送管道
public class MessageProviderImpl implements IMessageProvider {
    @Resource
    private MessageChannel output;//消息发送通道
    @Override
    public String send() {
        String message= UUID.randomUUID().toString();
        output.send(MessageBuilder
        .withPayload(message)
        .build());

        System.out.println("**********message= "+message);

        return null;
    }
}
