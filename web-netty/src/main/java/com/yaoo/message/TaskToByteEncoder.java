package com.yaoo.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class TaskToByteEncoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message msg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(msg.toString().getBytes(StandardCharsets.UTF_8));
    }
}
