package com.iqy.im.netty.handler;

import com.iqy.im.domain.Message;
import com.iqy.im.enums.MessageActionEnum;
import com.iqy.im.netty.UserChannelRel;
import com.iqy.im.netty.packet.Msg;
import com.iqy.im.netty.packet.Packet;
import com.iqy.im.service.MessageService;
import com.iqy.im.util.JsonUtil;
import com.iqy.im.util.SpringContextUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class SingleChatHandler extends SimpleChannelInboundHandler<Packet> {

    private static final ChannelGroup CHANNELS = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private final MessageService messageService;

    public SingleChatHandler() {
        this.messageService = SpringContextUtil.getBean(MessageService.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {
        Msg msg = JsonUtil.jsonToObject(packet.getExpand(), Msg.class);
        if (null == msg) {
            return;
        }
        Message message = messageService.save(msg);
        packet.setMsg(message);

        Channel toChannel = UserChannelRel.get(msg.getTo());
        if (null == toChannel || !toChannel.isOpen()) {
            // TODO 用户离线，推送消息
            log.info("user offline: {}", msg.getTo());
        } else {
            Channel c = CHANNELS.find(toChannel.id());
            if (null != c) {
                c.writeAndFlush(packet);
            } else {
                UserChannelRel.remove(msg.getTo());
                // TODO 用户离线，推送消息
                log.info("user offline: {}", msg.getTo());
            }
        }
    }

    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        if (super.acceptInboundMessage(msg)) {
            Packet packet = (Packet) msg;
            return packet.getAction().equals(MessageActionEnum.CHAT.getCode());
        }
        return false;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.debug("handlerAdded");
        CHANNELS.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.debug("handlerRemoved");
        CHANNELS.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
        CHANNELS.remove(ctx.channel());
    }
}
