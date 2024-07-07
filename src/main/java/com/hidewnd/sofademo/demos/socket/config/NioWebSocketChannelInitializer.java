package com.hidewnd.sofademo.demos.socket.config;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NioWebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private SockProperties sockProperties;
    @Autowired
    private NioWebSocketHandler nioWebSocketHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline()
                .addLast(new HttpServerCodec())
                .addLast(new ChunkedWriteHandler())
                .addLast(new HttpObjectAggregator(8192))
                .addLast(nioWebSocketHandler)
                .addLast(new WebSocketServerProtocolHandler(sockProperties.getPath(), null, true, 65536));
    }

}
