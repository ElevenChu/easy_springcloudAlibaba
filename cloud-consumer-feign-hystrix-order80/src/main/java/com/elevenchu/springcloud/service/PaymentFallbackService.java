package com.elevenchu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements  PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return null;
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return null;
    }
}
