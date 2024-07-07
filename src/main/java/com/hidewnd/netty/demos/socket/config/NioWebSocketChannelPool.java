package com.hidewnd.netty.demos.socket.config;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Getter
@Component
public class NioWebSocketChannelPool {

    private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public final ConcurrentHashMap<String, Channel> userIdMap = new ConcurrentHashMap<>();

    /**
     * 新增一个客户端通道
     *
     * @param channel
     */
    public void addChannel(Channel channel) {
        channels.add(channel);
        userIdMap.put(channel.id().asLongText(), channel);
    }

    /**
     * 移除一个客户端连接通道
     *
     * @param channel
     */
    public void removeChannel(Channel channel) {
        channels.remove(channel);
    }

    public Channel getChannel(String id){
        return userIdMap.get(id);
    }

}
