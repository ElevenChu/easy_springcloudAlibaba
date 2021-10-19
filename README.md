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



## Ribbon

：Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端负载均衡的工具

： 主要功能是提供客户端的软件负载均衡算法和服务调用

：简单的说，就是在配置文件中列出Load Balancer(简称LB)后面所有的机器，Ribbon会自动的帮助你基于某种规则(如简单轮询，随机连接等）去连接这些机器。我们很容易使用Ribbon实现自定义的负载均衡算法。

LB负载均衡(Load Balance)是什么？

：简单的说就是将用户的请求平摊的分配到多个服务上，从而达到系统的HA (高可用)

Ribbon本地负载均衡客户端VS Nginx服务端负载均衡区别

Nginx是服务器负载均衡，客户端所有请求都会交给nginx，然后由nginx实现转发请求。即负载均衡是由服务端实现的。

Ribbon本地负载均衡，在调用微服务接口时候，会在注册中心上获取注册信息服务列表之后缓存到JVM本地，从而在本地实现RPC远程服务调用技术。

集中式LB

：即在服务的消费方和提供方之间使用独立的LB设施(可以是硬件，如F5, 也可以是软件，如nginx)，由该设施负责把访问请求通过某种策略转发至服务的提供方;

进程内LB

：将LB逻辑集成到消费方，消费方从服务注册中心获知有哪些地址可用，然后自己再从这些地址中选择出一个合适的服务器。

Ribbon就属于进程内LB，它只是一个类库，集成于消费方进程，消费方通过它来获取到服务提供方的地址。

一句话：

负载均衡 + RestTemplate调用

Ribbon默认负载轮询算法原理：rest接口第几次请求数 % 服务器集群总数量 = 实际调用服务器位置下标，每次服务重启动后rest接口计数从1开始。



