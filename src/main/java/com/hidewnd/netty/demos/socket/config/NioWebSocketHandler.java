package com.hidewnd.netty.demos.socket.config;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable
public class NioWebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Autowired
    private NioWebSocketChannelPool webSocketChannelPool;
    @Autowired
    private SockProperties webSocketProperties;




    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("客户端连接：{}", ctx.channel().id());
        webSocketChannelPool.addChannel(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("客户端断开连接：{}", ctx.channel().id());
        webSocketChannelPool.removeChannel(ctx.channel());
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.channel().flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // 根据请求数据类型进行分发处理
        if (frame instanceof PingWebSocketFrame) {
            pingWebSocketFrameHandler(ctx, (PingWebSocketFrame) frame);
        } else if (frame instanceof TextWebSocketFrame) {
            textWebSocketFrameHandler(ctx, (TextWebSocketFrame) frame);
        } else if (frame instanceof CloseWebSocketFrame) {
            closeWebSocketFrameHandler(ctx, (CloseWebSocketFrame) frame);
        }


    }

    private void closeWebSocketFrameHandler(ChannelHandlerContext ctx, CloseWebSocketFrame frame) {
        ctx.close();
    }

    private void textWebSocketFrameHandler(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        TextWebSocketFrame retain = frame.retain();
        String text = retain.text();
        log.info("请求数据：{}", text);
        ctx.channel().writeAndFlush(new TextWebSocketFrame("{\"code\":200, \"msg\":\"连接成功\"}"));
    }

    private void pingWebSocketFrameHandler(ChannelHandlerContext ctx, PingWebSocketFrame frame) {
        ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
    }

}
