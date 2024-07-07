package com.hidewnd.netty.demos.web;

import com.hidewnd.netty.demos.socket.config.NioWebSocketChannelPool;
import com.hidewnd.netty.demos.web.dto.R;
import com.hidewnd.netty.demos.web.dto.TestDto;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class SocketController {

    @Autowired
    private NioWebSocketChannelPool nioWebSocketChannelPool;

    @PostMapping("/send")
    public R<String> test(@RequestBody TestDto dto) {
        Channel channel = nioWebSocketChannelPool.getChannel(dto.getUserId());
        if (channel != null) {
            channel.writeAndFlush(new TextWebSocketFrame(dto.getText()));
        }
        return R.success("发送成功");
    }

    @PostMapping("/send/all")
    public R<String> sendAll(@RequestBody TestDto dto) {
        for (Channel value : nioWebSocketChannelPool.userIdMap.values()) {
            value.writeAndFlush(new TextWebSocketFrame(dto.getText()));
        }
        return R.success("发送成功");
    }

    @PostMapping("/list")
    public R<List<String>> list() {
        return R.success("请求成功", new ArrayList<>(nioWebSocketChannelPool.userIdMap.keySet()));
    }
}
