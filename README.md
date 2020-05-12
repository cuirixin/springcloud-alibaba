# springcloud-alibaba
Springboot+AlibabaCloud
1、nacos 注册中心 [控制台](http://192.168.33.10:8848)
2、Fegin(RestClient) + Ribbon(客户端负载均衡)
3、sentinel 流量控制，断路、限流的使用, [控制台](http://192.168.33.10:8080)
4、Gateway 网关
5、Sleuth + Zipkin 链路跟踪
6、RocketMQ 消息服务 [控制台](http://192.168.33.10:7000)



# 依赖服务启动

## Docker (Vagrant)
```shell script
docker start mymysql
rocketmqnamesrv_start
rocketmqbroker_start
nacos_start
sentinel_start # # 注意必须是8080端口，不知道为什么！！！！！！！
```
