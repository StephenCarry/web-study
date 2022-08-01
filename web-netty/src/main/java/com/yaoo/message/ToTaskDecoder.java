package com.yaoo.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ToTaskDecoder extends ReplayingDecoder<ToTaskDecoder.LiveState> {
    public enum LiveState {
        TYPE,
        LENGTH,
        CONTENT
    }

    private Message message = new Message();

    public ToTaskDecoder() {
        super(LiveState.TYPE);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (state()) {
            case TYPE:
                byte type = byteBuf.readByte();
                message.setType(type);
                checkpoint(LiveState.LENGTH);
                break;
            case LENGTH:
                int len = byteBuf.readInt();
                message.setLength(len);
                if (len > 0) {
                    checkpoint(LiveState.CONTENT);
                } else {
                    list.add(message);
                    checkpoint(LiveState.TYPE);
                }
                break;
            case CONTENT:
                byte[] bytes = new byte[message.getLength()];
                byteBuf.readBytes(bytes);
                String content = new String(bytes);
                message.setContent(content);
                list.add(message);
                message = new Message();
                checkpoint(LiveState.TYPE);
                break;
            default:
                throw new IllegalStateException("invalid state: "+ state());
        }
    }
}
