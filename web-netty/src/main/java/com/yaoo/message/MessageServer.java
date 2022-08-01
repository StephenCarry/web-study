package com.yaoo.message;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MessageServer {
    private final int port;

    public MessageServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new MessageServer(8080).start();
    }

    public void start() throws Exception {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        System.out.println("init channel :"+socketChannel);
                        socketChannel.pipeline()
                                .addLast("decoder", new ToTaskDecoder())
                                .addLast("handler", new MessageHandler())
                                .addLast("encoder", new TaskToByteEncoder());
                    }
                });
        serverBootstrap.bind(port).sync();
    }
}
