package com.yaoo;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) {
        System.out.println("class: "+fullHttpRequest.getClass().getName()+new Date().getTime());

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer("test".getBytes(StandardCharsets.UTF_8))
        );

        HttpHeaders headers = response.headers();
        headers.add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN+";"+HttpHeaderValues.CHARSET+"=UTF-8");
        headers.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        headers.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);

        channelHandlerContext.write(response);
    }
}
