package com.iqy.im.netty;

import com.iqy.im.netty.initializer.NioSocketChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImServer {
    private final ServerBootstrap bootstrap;
    private final EventLoopGroup mainGroup;
    private final EventLoopGroup workerGroup;

    public ImServer() {
        bootstrap = new ServerBootstrap();
        mainGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
    }

    public void start(int port) {
        try {
            bootstrap.group(mainGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NioSocketChannelInitializer());
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            log.info("Netty started at port: {}", port);
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mainGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
