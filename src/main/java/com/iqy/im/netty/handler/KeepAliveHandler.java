package com.iqy.im.netty.handler;

import com.iqy.im.enums.MessageActionEnum;
import com.iqy.im.netty.packet.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class KeepAliveHandler extends SimpleChannelInboundHandler<Packet> {

    private final Packet keepAlivePacket;

    public KeepAliveHandler() {
        keepAlivePacket = Packet.builder()
                .action(MessageActionEnum.KEEP_ALIVE.getCode())
                .build();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {
        log.debug("received heartbeat from: {}", ctx.channel());
        ctx.channel().writeAndFlush(keepAlivePacket);
    }

    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        if (super.acceptInboundMessage(msg)) {
            Packet packet = (Packet) msg;
            return packet.getAction().equals(MessageActionEnum.KEEP_ALIVE.getCode());
        }
        return false;
    }
}
