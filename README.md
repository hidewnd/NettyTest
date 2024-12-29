

> Tech stack

- java17

- spring-boot 3.3.1
- netty 4.1.111.Final

> Run

- jdk

```shell
java -jar netty-test.jar
```

- docker

```shell
docker build -t netty-test:0.1 .

docker run -d --net=bridge --name=netty-test \
-v /Users/hidewnd/tools/winds/nettyTest/application.properties:/app/application.properties \
-p 9002:9002 \
-p 5055:5055 \
netty-test:0.1

```

> APIs

send message to all channel

`post: http://[address]:{server.port}/test/send/all`

```json
{
  "text": {
    "action": 2001,
    "data": {
      "zone": "电信区",
      "server": "梦江南",
      "status": 1
    }
  }
}
```
ws: 
```json
{"action":2001,"data":{"zone":"电信区","server":"梦江南","status":1}}
```


