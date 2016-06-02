# tcp2httpbridge

## 功能描述

将原TCP通信的C/S结构软件的数据经由HTTP转发。即：

> SERVER <-TCP-> CLIENT

转为：

> SERVER <-TCP-> BRIDGE <- HTTP -> BRIDGE <- TCP -> CLIENT

由此实现了C/S间穿过CDN,防火墙等复杂网络设备下的通信。

*此程序目前用于zabbix_agent (ServerActive Mode) 下的监控服务*

## 使用

### 启动


```bash

// 启动服务端HTTP服务
java -jar tcp2httpbridge.jar HTTP
// 启动客户端TCP服务
java -jar tcp2httpbridge.jar TCP
// 同时启动TCP和HTTP服务
java -jar tcp2httpbridge.jar ALL

```

### 配置

通过app.properties文件进行程序相关设置

```properties

# 应用HTTP项目名称
app.project=tcp2httpbridge
# 最大TCP/HTTP读取缓冲大小
app.maxbuffer=20480
# 最大HTTP监听数量
app.maxhttphandler=100


# TCP模式时，本地TCP监听端口(zabbix_agent配置中将ServerActive端口改为此即可被监听)
local.tcp.port=1234
# TCP交互超时
local.tcp.timeout=10
# HTTP模式时，本地HTTP监听端口
local.http.port=8888


# 转发HTTP服务器地址(此处指部署有zabbix_server的服务器地址)
remote.http.server=http://xxx.xxx.xxx.xxx
# 转发HTTP服务端口
remote.http.port=8888
# 发送TCP目的地址(IP或域名均可)
remote.tcp.server=localhost
# 发送TCP目的端口(10051对应zabbix_server监听agent数据的端口)
remote.tcp.port=10051


# HTTP请求API地址
api.zabbix=zabbix

```

### 查询参数与运行状况统计信息

要求HTTP服务启动才行，访问地址:health

如：localhost:8888/tcp2httpbridge/health

### 与zabbix配合

#### agentd端：

agentd端桥接器启动为TCP模式

将serverActive指向桥接器监听端口：如 127.0.0.1:1234

桥接器远程http目的地址指向部署有server的服务器上的桥接器监听地址，端口开放受限的可以通过nginx等方式转发

#### server端

server端桥接器启动为HTTP模式

将server端桥接器的远程TCP地址设为zabbix_server地址：如127.0.0.1:10051
