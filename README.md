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


## Consul分布式服务发现和配置管理系统（服务注册与发现）

:现已停更维护

功能有：

服务发现 - 提供HTTP和DNS两种发现方式。

健康监测 - 支持多种方式，HTTP、TCP、Docker、Shell脚本定制化

KV存储 - Key、Value的存储方式

多数据中心 - Consul支持多数据中心

可视化Web界面

### 三个注册中心异同点
![YPI($14VPV3F3CBVBZR@%CN](https://user-images.githubusercontent.com/57619422/137909913-b8943841-02af-4ddd-a82a-6a16465dd687.png)

CAP

![U)U}_D4SR8SSV46S7TW2N J](https://user-images.githubusercontent.com/57619422/137910012-bfbc64e7-3e1a-4257-8032-2ebc8b51e3d0.png)


![`V9 _YYN 54%H@(67}563YP](https://user-images.githubusercontent.com/57619422/137910153-9a565b6b-2c93-4dfe-9c15-792902897231.png)

AP架构： 当网络分区出现后，为了保证可用性，系统B可以返回旧值，保证系统的可用性。结论：违背了一致性C的要求，只满足可用性和分区容错，即AP
![image](https://user-images.githubusercontent.com/57619422/137910472-d44708b8-0d23-4dc2-9be0-7e7410127135.png)


CP架构： 当网络分区出现后，为了保证一致性，就必须拒接请求，否则无法保证一致性。结论：违背了可用性A的要求，只满足一致性和分区容错，即CP。
![image](https://user-images.githubusercontent.com/57619422/137910496-4e1fb6fd-b897-4640-b9d6-3e360f804b36.png)






