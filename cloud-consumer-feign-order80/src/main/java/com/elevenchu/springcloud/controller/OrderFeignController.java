package com.elevenchu.springcloud.controller;

import com.elevenchu.springcloud.entities.CommonResult;
import com.elevenchu.springcloud.entities.Payment;
import com.elevenchu.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
            return  paymentFeignService.getPaymentById(id);
    }
}
