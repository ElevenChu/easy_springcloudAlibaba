package com.elevenchu.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.elevenchu.springcloud.dao"})
public class MyBatisConfig {
}