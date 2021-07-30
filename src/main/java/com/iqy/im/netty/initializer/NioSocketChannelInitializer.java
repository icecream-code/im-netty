package com.iqy.im.netty.initializer;

import com.iqy.im.netty.handler.*;
import com.iqy.im.netty.handler.codec.PacketCodecHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class NioSocketChannelInitializer extends ChannelInitializer<NioSocketChannel> {
    private final HeartBeatHandler heartBeatHandler = new HeartBeatHandler();
    private final PacketCodecHandler packetCodecHandler = new PacketCodecHandler();
    private final AuthenticationHandler authenticationHandler = new AuthenticationHandler();
    private final AuthorizationHandler authorizationHandler = new AuthorizationHandler();
    private final KeepAliveHandler keepAliveHandler = new KeepAliveHandler();
    private final SingleChatHandler singleChatHandler = new SingleChatHandler();
    private final MessageSignHandler messageSignHandler = new MessageSignHandler();

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new HttpServerCodec()) // websocket 基于http协议，所以要有http编解码器
                .addLast(new ChunkedWriteHandler()) // 对大数据流支持
                .addLast(new HttpObjectAggregator(1024 * 64))
                .addLast(new IdleStateHandler(16, 18, 20))
                .addLast(heartBeatHandler)
                .addLast(new WebSocketServerProtocolHandler("/ws"))

                .addLast(packetCodecHandler)
                .addLast(authenticationHandler)
                .addLast(authorizationHandler)
                .addLast(keepAliveHandler)
                .addLast(singleChatHandler)
                .addLast(messageSignHandler);
    }
}
