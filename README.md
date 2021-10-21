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


## OpenFeign
前面在使用Ribbon+RestTemplate时，利用RestTemplate对http请求的封装处理，形成了一套模版化的调用方法。但是在实际开发中，由于对服务依赖的调用可能不止一处，往往一个接口会被多处调用，所以通常都会针对每个微服务自行封装一些客户端类来包装这些依赖服务的调用。所以，Feign在此基础上做了进一步封装，由他来帮助我们定义和实现依赖服务接口的定义。在Feign的实现下，我们只需创建一个接口并使用注解的方式来配置它(以前是Dao接口上面标注Mapper注解,现在是一个微服务接口上面标注一个Feign注解即可)，即可完成对服务提供方的接口绑定，简化了使用Spring cloud Ribbon时，自动封装服务调用客户端的开发量。


OpenFeign是Spring Cloud在Feign的基础上支持了SpringMVC的注解，如@RequesMapping等等。OpenFeign的@Feignclient可以解析SpringMVc的@RequestMapping注解下的接口，并通过动态代理的方式产生实现类，实现类中做负载均衡并调用其他服务。

![image](https://user-images.githubusercontent.com/57619422/137912508-db55aad8-0097-482c-a63c-d56059e37f29.png)


## Hystrix
：Hystrix是一个用于处理分布式系统的延迟和容错的开源库，在分布式系统里，许多依赖不可避免的会调用失败，比如超时、异常等，Hystrix能够保证在一个依赖出问题的情况下，不会导致整体服务失败，避免级联故障，以提高分布式系统的弹性。

：断路器”本身是一种开关装置，当某个服务单元发生故障之后，通过断路器的故障监控（类似熔断保险丝)，向调用方返回一个符合预期的、可处理的备选响应（FallBack)，而不是长时间的等待或者抛出调用方无法处理的异常，这样就保证了服务调用方的线程不会被长时间、不必要地占用，从而避免了故障在分布式系统中的蔓延，乃至雪崩。

：停止更新进入维护了，可使用Sentinel

服务降级：服务器忙，请稍后再试，不让客户端等待并立刻返回一个友好提示，fallback

哪些情况会触发降级？

:1程序运行导常

2超时

3服务熔断触发服务降级

4线程池/信号量打满也会导致服务降级

服务熔断:类比保险丝达到最大服务访问后，直接拒绝访问，拉闸限电，然后调用服务降级的方法并返回友好提示。

服务限流: 秒杀高并发等操作，严禁一窝蜂的过来拥挤，大家排队，一秒钟N个，有序进行.

熔断机制概述：熔断机制是应对雪崩效应的一种微服务链路保护机制。当扇出链路的某个微服务出错不可用或者响应时间太长时，会进行服务的降级，进而熔断该节点微服务的调用，快速返回错误的响应信息。当检测到该节点微服务调用响应正常后，恢复调用链路

## GateWay

:Spring生态系统之上构建的API网关服务

:Gateway旨在提供一种简单而有效的方式来对API进行路由，以及提供一些强大的过滤器功能，例如:熔断、限流、重试等。

功能：方向代理 鉴权 流量控制 熔断 日志监控

![image](https://user-images.githubusercontent.com/57619422/138203338-d562fae6-5d31-4ca5-b3ee-1dd349fbfed9.png)

特性有：
- 基于Spring Framework 5，Project Reactor和Spring Boot 2.0进行构建；
- 动态路由：能够匹配任何请求属性；
- 可以对路由指定Predicate (断言)和Filter(过滤器)；
- 集成Hystrix的断路器功能；
- 集成Spring Cloud 服务发现功能；
- 易于编写的Predicate (断言)和Filter (过滤器)；
- 请求限流功能；
- 支持路径重写。

三大核心概念

- Route(路由) - 路由是构建网关的基本模块,它由ID,目标URI,一系列的断言和过滤器组成,如断言为true则匹配该路由；
- Predicate(断言) - 参考的是Java8的java.util.function.Predicate，开发人员可以匹配HTTP请求中的所有内容(例如请求头或请求参数),如果请求与断言相匹配则进行路由；
- Filter(过滤) - 指的是Spring框架中GatewayFilter的实例,使用过滤器,可以在请求被路由前或者之后对请求进行修改。


## Config配置中心

微服务意味着要将单体应用中的业务拆分成一个个子服务，每个服务的粒度相对较小，因此系统中会出现大量的服务。由于每个服务都需要必要的配置信息才能运行，所以一套集中式的、动态的配置管理设施是必不可少的。

功能:

- 集中管理配置文件

- 不同环境不同配置，动态化的配置更新，分环境部署比如dev/test/prod/beta/release

- 运行期间动态调整配置，不再需要在每个服务部署的机器上编写配置文件，服务会向配置中心统一拉取配置自己的信息

- 当配置发生变动时，服务不需要重启即可感知到配置的变化并应用新的配置

- 将配置信息以REST接口的形式暴露 - post/crul访问刷新即可…


## Bus消息总线

:Spring Cloud Bus是用来将分布式系统的节点与轻量级消息系统链接起来的框架，它整合了Java的事件处理机制和消息中间件的功能。Spring Clud Bus目前支持RabbitMQ和Kafka。

功能:Spring Cloud Bus能管理和传播分布式系统间的消息，就像一个分布式执行器，可用于广播状态更改、事件推送等，也可以当作微服务间的通信通道。

什么是总线:在微服务架构的系统中，通常会使用轻量级的消息代理来构建一个共用的消息主题，并让系统中所有微服务实例都连接上来。由于该主题中产生的消息会被所有实例监听和消费，所以称它为消息总线。在总线上的各个实例，都可以方便地广播一些需要让其他连接在该主题上的实例都知道的消息。

利用消息总线触发一个服务端ConfigServer的/bus/refresh端点，而刷新所有客户端的配置
![image](https://user-images.githubusercontent.com/57619422/138206610-e7967dfe-5d97-4d6e-9b42-8fcf6ae06931.png)



## Stream 

：屏蔽底层消息中间件的差异，降低切换成本，统一消息的编程模型。

:官方定义Spring Cloud Stream是一个构建消息驱动微服务的框架,应用程序通过inputs或者 outputs 来与Spring Cloud Stream中binder对象交互.通过我们配置来binding(绑定)，而Spring Cloud Stream 的binder对象负责与消息中间件交互。

![image](https://user-images.githubusercontent.com/57619422/138242455-c0b7e7ae-378b-47c5-b4f8-a29d0e6a2b50.png)


