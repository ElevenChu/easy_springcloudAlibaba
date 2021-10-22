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


## Sleuth

产生的原因：在微服务框架中，一个由客户端发起的请求在后端系统中会经过多个不同的的服务节点调用来协同产生最后的请求结果，每一个前段请求都会形成一条复杂的分布式服务调用链路，链路中的任何一环出现高延时或错误都会引起整个请求最后的失败

- Spring Cloud Sleuth提供了一套完整的服务跟踪的解决方案
- 在分布式系统中提供追踪解决方案并且兼容支持了zipkin

完整的调用链路

：表示一请求链路，一条链路通过Trace ld唯一标识，Span标识发起的请求信息，各span通过parent id关联起来

—条链路通过Trace ld唯一标识，Span标识发起的请求信息，各span通过parent id关联起来
![image](https://user-images.githubusercontent.com/57619422/138246725-e45cc74b-dc08-4247-bef0-f78aef237c2c.png)

- Trace：类似于树结构的Span集合，表示一条调用链路，存在唯一标识
- span：表示调用链路来源，通俗的理解span就是一次请求信息

##  Cloud Alibaba 
:Spring Cloud Alibaba 致力于提供微服务开发的一站式解决方案。此项目包含开发分布式应用微服务的必需组件，方便开发者通过 Spring Cloud 编程模型轻松使用这些组件来开发分布式应用服务。

依托 Spring Cloud Alibaba，您只需要添加一些注解和少量配置，就可以将 Spring Cloud 应用接入阿里微服务解决方案，通过阿里中间件来迅速搭建分布式应用系统。

功能：
- 服务限流降级：默认支持 WebServlet、WebFlux, OpenFeign、RestTemplate、Spring Cloud Gateway, Zuul, Dubbo 和 RocketMQ 限流降级功能的接入，可以在运行时通过控制台实时修改限流降级规则，还支持查看限流降级 Metrics 监控。
- 服务注册与发现：适配 Spring Cloud 服务注册与发现标准，默认集成了 Ribbon 的支持。
- 分布式配置管理：支持分布式系统中的外部化配置，配置更改时自动刷新。
- 消息驱动能力：基于 Spring Cloud Stream 为微服务应用构建消息驱动能力。
- 分布式事务：使用 @GlobalTransactional 注解， 高效并且对业务零侵入地解决分布式事务问题。
- 阿里云对象存储：阿里云提供的海量、安全、低成本、高可靠的云存储服务。支持在任何应用、任何时间、任何地点存储和访问任意类型的数据。
- 分布式任务调度：提供秒级、精准、高可靠、高可用的定时（基于 Cron 表达式）任务调度服务。同时提供分布式的任务执行模型，如网格任务。网格任务支持海量子任务均匀分配到所有 Worker（schedulerx-client）上执行。
- 阿里云短信服务：覆盖全球的短信服务，友好、高效、智能的互联化通讯能力，帮助企业迅速搭建客户触达通道。

组件:
- Sentinel：把流量作为切入点，从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性。
- Nacos：一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。
- RocketMQ：一款开源的分布式消息系统，基于高可用分布式集群技术，提供低延时的、高可靠的消息发布与订阅服务。
- Dubbo：Apache Dubbo™ 是一款高性能 Java RPC 框架。
- Seata：阿里巴巴开源产品，一个易于使用的高性能微服务分布式事务解决方案。
- Alibaba Cloud OSS: 阿里云对象存储服务（Object Storage Service，简称 OSS），是阿里云提供的海量、安全、低成本、高可靠的云存储服务。您可以在任何应用、任何时间、任何地点存储和访问任意类型的数据。
- Alibaba Cloud SchedulerX: 阿里中间件团队开发的一款分布式任务调度产品，提供秒级、精准、高可靠、高可用的定时（基于 Cron 表达式）任务调度服务。
- Alibaba Cloud SMS: 覆盖全球的短信服务，友好、高效、智能的互联化通讯能力，帮助企业迅速搭建客户触达通道。

### Nacos
:一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台

：Nacos就是注册中心＋配置中心的组合 -> Nacos = Eureka+Config+Bus,替代Eureka做服务注册中心。替代Config做服务配置中心

![image](https://user-images.githubusercontent.com/57619422/138251697-a66144ad-4b00-4219-8a7a-e542224e4b5d.png)

![image](https://user-images.githubusercontent.com/57619422/138252001-ec0d96c6-f391-4af3-8474-53cc4d9257ca.png)


![image](https://user-images.githubusercontent.com/57619422/138252726-ea6bbfa4-2478-4c4c-93f4-02face4a2a27.png)

### Nacos支持AP与CP模式的切换

C（一致性）是所有节点在同一时间看到的数据是一致的;而A(可用性/高可用)的定义是所有的请求都会收到响应

### Nacos与配置中心
![image](https://user-images.githubusercontent.com/57619422/138378382-a9bd5090-bfbf-4e61-a2fd-3ba9968caa0c.png)

![image](https://user-images.githubusercontent.com/57619422/138378366-422db820-33e7-4b7d-a271-ca881a6d01a9.png)

![image](https://user-images.githubusercontent.com/57619422/138378396-f16c77ee-fcb6-4966-af01-2f0417b18265.png)

### Nacos集群
![image](https://user-images.githubusercontent.com/57619422/138379140-3ae61202-a3bd-4fa3-858a-f81b0f412895.png)

![image](https://user-images.githubusercontent.com/57619422/138379147-df8284fc-3647-4b22-9710-1ba7eedbb201.png)



默认Nacos使用嵌入式数据库实现数据的存储（Nacos默认自带的是嵌入式数据库derby）。所以，如果启动多个默认配置下的Nacos节点，数据存储是存在一致性问题的。为了解决这个问题，Nacos采用了集中式存储的方式来支持集群化部署，目前只支持MySQL的存储。

Nacos支持三种部署模式

- 单机模式-用于测试和单机试用。
- 集群模式-用于生产环境，确保高可用。
- 多集群模式-用于多数据中心场景。

![image](https://user-images.githubusercontent.com/57619422/138388547-278a9db0-6d23-42eb-bcba-db5d643e73ce.png)


### Sentinel

随着微服务的流行，服务和服务之间的稳定性变得越来越重要。Sentinel 以流量为切入点，从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性。

特征：
- 丰富的应用场景：Sentinel 承接了阿里巴巴近 10 年的双十一大促流量的核心场景，例如秒杀（即突发流量控制在系统容量可以承受的范围）、消息削峰填谷、集群流量控制、实时熔断下游不可用应用等。
- 完备的实时监控：Sentinel 同时提供实时的监控功能。您可以在控制台中看到接入应用的单台机器秒级数据，甚至 500 台以下规模的集群的汇总运行情况。
- 广泛的开源生态：Sentinel 提供开箱即用的与其它开源框架/库的整合模块，例如与 Spring Cloud、Dubbo、gRPC 的整合。您只需要引入相应的依赖并进行简单的配置即可快速地接入 Sentinel。
- 完善的 SPI 扩展点：Sentinel 提供简单易用、完善的 SPI 扩展接口。您可以通过实现扩展接口来快速地定制逻辑。例如定制规则管理、适配动态数据源等。


Hystrix：

需要我们程序员自己手工搭建监控平台

没有一套web界面可以给我们进行更加细粒度化得配置流控、速率控制、服务熔断、服务降级

Sentinel

：单独一个组件，可以独立出来。

：直接界面化的细粒度统一配置。

### Sentinel流控规则

![image](https://user-images.githubusercontent.com/57619422/138389356-cb11a0c8-65d1-49d7-9d0f-8b9dcd9fbeef.png)

资源名：唯一名称，默认请求路径。

针对来源：Sentinel可以针对调用者进行限流，填写微服务名，默认default（不区分来源）。

阈值类型/单机阈值：
- QPS(每秒钟的请求数量)︰当调用该API的QPS达到阈值的时候，进行限流。
- 线程数：当调用该API的线程数达到阈值的时候，进行限流。
是否集群：不需要集群。

流控模式：

- 直接：API达到限流条件时，直接限流。
- 关联：当关联的资源达到阈值时，就限流自己。当自己关联的资源达到阈值时，就限流自己当与A关联的资源B达到阀值后，就限流A自己（B惹事，A挂了）
- 链路：只记录指定链路上的流量（指定资源从入口资源进来的流量，如果达到阈值，就进行限流)【API级别的针对来源】。


流控效果：
- 快速失败：直接失败，抛异常。
- Warm up：根据Code Factor（冷加载因子，默认3）的值，从阈值/codeFactor，经过预热时长，才达到设置的QPS阈值。
- 排队等待：匀速排队，让请求以匀速的速度通过，阈值类型必须设置为QPS，否则无效。


**流控 预热**

Warm Up（RuleConstant.CONTROL_BEHAVIOR_WARM_UP）方式，即预热/冷启动方式。当系统长期处于低水位的情况下，当流量突然增加时，直接把系统拉升到高水位可能瞬间把系统压垮。通过"冷启动"，让通过的流量缓慢增加，在一定时间内逐渐增加到阈值上限，给冷系统一个预热的时间，避免冷系统被压垮。详细文档可以参考 流量控制 - Warm Up 文档，具体的例子可以参见 WarmUpFlowDemo。

通常冷启动的过程系统允许通过的 QPS 曲线如下图所示：
![image](https://user-images.githubusercontent.com/57619422/138390156-9e7b3daa-c612-44b6-87e7-52ebd2266772.png)

默认coldFactor为3，即请求QPS 从 threshold / 3开始，经预热时长逐渐升至设定的QPS阈值。link

案例，阀值为10+预热时长设置5秒。

系统初始化的阀值为10/ 3约等于3,即阀值刚开始为3;然后过了5秒后阀值才慢慢升高恢复到10
![image](https://user-images.githubusercontent.com/57619422/138390203-5cc9888c-2852-4add-8527-a9e4b65077a4.png)

**sentinel降级**

熔断降级概述

除了流量控制以外，对调用链路中不稳定的资源进行熔断降级也是保障高可用的重要措施之一。一个服务常常会调用别的模块，可能是另外的一个远程服务、数据库，或者第三方 API 等。例如，支付的时候，可能需要远程调用银联提供的 API；查询某个商品的价格，可能需要进行数据库查询。然而，这个被依赖服务的稳定性是不能保证的。如果依赖的服务出现了不稳定的情况，请求的响应时间变长，那么调用服务的方法的响应时间也会变长，线程会产生堆积，最终可能耗尽业务自身的线程池，服务本身也变得不可用。

现代微服务架构都是分布式的，由非常多的服务组成。不同服务之间相互调用，组成复杂的调用链路。以上的问题在链路调用中会产生放大的效果。复杂链路上的某一环不稳定，就可能会层层级联，最终导致整个链路都不可用。因此我们需要对不稳定的弱依赖服务调用进行熔断降级，暂时切断不稳定调用，避免局部不稳定因素导致整体的雪崩。熔断降级作为保护自身的手段，通常在客户端（调用端）进行配置。

![image](https://user-images.githubusercontent.com/57619422/138390568-0a30761b-88f0-4093-8dae-b6e4d2c414fd.png)

RT（平均响应时间，秒级）

- 平均响应时间 超出阈值 且 在时间窗口内通过的请求>=5，两个条件同时满足后触发降级。
- 窗口期过后关闭断路器。
- RT最大4900（更大的需要通过-Dcsp.sentinel.statistic.max.rt=XXXX才能生效）。


异常比列（秒级）
- QPS >= 5且异常比例（秒级统计）超过阈值时，触发降级;时间窗口结束后，关闭降级 。

异常数(分钟级)
- 异常数(分钟统计）超过阈值时，触发降级;时间窗口结束后，关闭降级


Sentinel熔断降级会在调用链路中某个资源出现不稳定状态时（例如调用超时或异常比例升高)，对这个资源的调用进行限制，让请求快速失败，避免影响到其它的资源而导致级联错误。

当资源被降级后，在接下来的降级时间窗口之内，对该资源的调用都自动熔断（默认行为是抛出 DegradeException）。

Sentinei的断路器是没有类似Hystrix半开状态的。(Sentinei 1.8.0 已有半开状态)

半开的状态系统自动去检测是否请求有异常，没有异常就关闭断路器恢复使用，有异常则继续打开断路器不可用。


***Sentinel 热点Key***

何为热点？热点即经常访问的数据。很多时候我们希望统计某个热点数据中访问频次最高的 Top K 数据，并对其访问进行限制。比如：

- 商品 ID 为参数，统计一段时间内最常购买的商品 ID 并进行限制
- 用户 ID 为参数，针对一段时间内频繁访问的用户 ID 进行限制

热点参数限流会统计传入参数中的热点参数，并根据配置的限流阈值与模式，对包含热点参数的资源调用进行限流。热点参数限流可以看做是一种特殊的流量控制，仅对包含热点参数的资源调用生效。


**BlockHandler与FallBack**
- fallback只负责业务异常
- blockHandler只负责sentinel控制台配置违规
- 若blockHandler和fallback 都进行了配置，则被限流降级而抛出BlockException时只会进入blockHandler处理逻辑。

熔断框架比较
![image](https://user-images.githubusercontent.com/57619422/138413074-361c8d0c-f719-4614-b03a-d0129908ad6c.png)


**Sentinel持久化规则**
一旦我们重启应用，sentinel规则将消失，生产环境需要将配置规则进行持久化。

:将限流配置规则持久化进Nacos保存，只要刷新8401某个rest地址，sentinel控制台的流控规则就能看到，只要Nacos里面的配置不删除，针对8401上sentinel上的流控规则持续有效
![image](https://user-images.githubusercontent.com/57619422/138415298-2c3603b4-6dd1-4a85-a77f-46bcacb8802d.png)


***分布式事务的由来***
单体应用被拆分成微服务应用，原来的三个模块被拆分成三个独立的应用,分别使用三个独立的数据源，业务操作需要调用三三 个服务来完成。此时每个服务内部的数据一致性由本地事务来保证， 但是全局的数据一致性问题没法保证。

**一句话：一次业务操作需要跨多个数据源或需要跨多个系统进行远程调用，就会产生分布式事务问题。**

### Seata
Seata是一款开源的分布式事务解决方案，致力于在微服务架构下提供高性能和简单易用的分布式事务服务。

分布式事务处理过程的一ID+三组件模型：

**Transaction ID XID 全局唯一的事务ID**

三组件概念:
- TC (Transaction Coordinator) - 事务协调者：维护全局和分支事务的状态，驱动全局事务提交或回滚。
- TM (Transaction Manager) - 事务管理器：定义全局事务的范围：开始全局事务、提交或回滚全局事务。
- RM (Resource Manager) - 资源管理器：管理分支事务处理的资源，与TC交谈以注册分支事务和报告分支事务的状态，并驱动分支事务提交或回滚。

处理过程:

1.TM向TC申请开启一个全局事务，全局事务创建成功并生成一个全局唯一的XID；

2.XID在微服务调用链路的上下文中传播；

3.RM向TC注册分支事务，将其纳入XID对应全局事务的管辖；

4.TM向TC发起针对XID的全局提交或回滚决议；

5.TC调度XID下管辖的全部分支事务完成提交或回滚请求。


![image](https://user-images.githubusercontent.com/57619422/138416121-03f48c7f-86f2-4bae-a5fe-88c9107e7a52.png)

超时异常，加了@GlobalTransactional
![image](https://user-images.githubusercontent.com/57619422/138418180-030fdb3e-b092-4f0d-8b70-ec708eb993ba.png)


分布式事务的执行流程:

- TM开启分布式事务(TM向TC注册全局事务记录) ;
- 按业务场景，编排数据库、服务等事务内资源(RM向TC汇报资源准备状态) ;
- TM结束分布式事务，事务一阶段结束(TM通知TC提交/回滚分布式事务) ;
- TC汇总事务信息，决定分布式事务是提交还是回滚；
- TC通知所有RM提交/回滚资源，事务二阶段结束。


