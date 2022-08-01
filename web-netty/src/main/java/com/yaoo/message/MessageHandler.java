package com.yaoo.message;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.yaoo.message.Message.HEART;
import static com.yaoo.message.Message.MSG;

public class MessageHandler extends SimpleChannelInboundHandler<Message> {

    private static final Map<Integer, ChannelCache> cacheMap = new HashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        Channel channel = channelHandlerContext.channel();
        final int hashCode = channel.hashCode();
        System.out.println("channel hashCode: "+hashCode+" msg: "+message.content+" cache: "+cacheMap.size());

        if (!cacheMap.containsKey(hashCode)) {
            System.out.println("channel put key: "+ hashCode);
            channel.closeFuture().addListener(future -> {
                System.out.println("channel close, remove key: "+ hashCode);
                cacheMap.remove(hashCode);
            });
            ScheduledFuture<ChannelFuture> scheduledFuture = channelHandlerContext.executor()
                    .schedule((Callable<ChannelFuture>) channel::close, 30, TimeUnit.SECONDS);
            cacheMap.put(hashCode, new ChannelCache(channel, scheduledFuture));
        }

        switch (message.getType()) {
            case HEART:
                ChannelCache channelCache = cacheMap.get(hashCode);
                ScheduledFuture<ChannelFuture> scheduledFuture = channelHandlerContext.executor()
                        .schedule((Callable<ChannelFuture>) channel::close, 30, TimeUnit.SECONDS);
                channelCache.getScheduledFuture().cancel(true);
                channelCache.setScheduledFuture(scheduledFuture);
                channelHandlerContext.channel().writeAndFlush(message);
            case MSG:
                cacheMap.forEach((key, value) -> {
                    Channel otherChannel = value.getChannel();
                    otherChannel.writeAndFlush(message);
                });
        }
    }
}
