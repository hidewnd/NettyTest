package com.hidewnd.sofademo.demos.socket.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NioWebSocketServer implements InitializingBean, DisposableBean {
    @Autowired
    private SockProperties sockProperties;
    @Autowired
    private NioWebSocketChannelInitializer webSocketChannelInitializer;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;
    private ChannelFuture channelFuture;


    @Override
    public void destroy() throws Exception {
        log.info("Shutting down Netty server...");
        if (bossGroup != null)
            bossGroup.shutdownGracefully().sync();
        if (workGroup != null)
            workGroup.shutdownGracefully().sync();
        if (channelFuture != null)
            channelFuture.channel().closeFuture().syncUninterruptibly();
        log.info("Netty server shutdown.");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            bossGroup = new NioEventLoopGroup(sockProperties.getBoss());
            workGroup = new NioEventLoopGroup(sockProperties.getWorker());

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024)
                    .group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(sockProperties.getPort())
                    .childHandler(webSocketChannelInitializer);

            channelFuture = serverBootstrap.bind().sync();
        } finally {
            if (channelFuture != null && channelFuture.isSuccess()) {
                log.info("Netty server startup on port: {} (websocket) with context path '{}'", sockProperties.getPort(), sockProperties.getPath());
            } else {
                log.error("Netty server startup failed.");
                if (bossGroup != null)
                    bossGroup.shutdownGracefully().sync();
                if (workGroup != null)
                    workGroup.shutdownGracefully().sync();
            }
        }

    }
}
