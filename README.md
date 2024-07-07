

> Tech stack

- java17

- spring-boot 3.3.1
- netty 4.1.111.Final



> APIs

connect to ws

`ws://[address]:5555/ws`

get channel Ids

`post: http://[address]:8080/test/list`

send message to specified channel

`post: http://[address]:8080/test/send`

```json
{
    "userId": "{{userId}}"
    "text": "{{anything your want}}"
}
```

send message to all channel

`post: http://[address]:8080/test/send/all`

```json
{
    "text": "{{anything your want}}"
}
```

