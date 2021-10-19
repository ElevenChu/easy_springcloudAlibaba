# easy_springcloudAlibaba

这个仓库是有关springcloud生态体系的简单综合, 分模块,分组件的初步简单使用,是我学习分布式微服务的第一步


## Eureka服务注册与发现中心

：现已停更维护，用SpringCloud Alibaba替代。
：Eureka包含两个组件 Eureka Server和Eureka Client。Eureka Server提供服务注册服务；EurekaClient通过注册中心进行访问。
：服务注册：将服务信息注册进注册中心；服务发现：从注册中心上获取服务信息
![G LM)SZ5543M@EG(WMFBHF4](https://user-images.githubusercontent.com/57619422/137907022-36a10e68-5cfd-4dcb-8a14-d801a68cc4e7.png)

问题:微服务RPC远程服务调用最核心的是什么？

高可用，试想你的注册中心只有一个，万一它出故障了，会导致整个为服务环境不可用。

解决办法：搭建Eureka注册中心集群，实现负载均衡+故障容错。


