package com.iqy.im.netty.handler;

import com.iqy.im.enums.MessageActionEnum;
import com.iqy.im.netty.packet.Packet;
import com.iqy.im.service.MessageService;
import com.iqy.im.util.SpringContextUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class MessageSignHandler extends SimpleChannelInboundHandler<Packet> {

    private final MessageService messageService;

    public MessageSignHandler() {
        this.messageService = SpringContextUtil.getBean(MessageService.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {
        // 签收消息ID在扩展字段中，多个以逗号隔开
        String string = packet.getExpand();

        if (StringUtils.isEmpty(string)) {
            return;
        }
        String[] mids = string.split(",");

        List<String> msgIdList = new ArrayList<>();
        for (String mid : mids) {
            if (!StringUtils.isEmpty(mid)) {
                msgIdList.add(mid);
            }
        }

        if (!msgIdList.isEmpty()) {
            messageService.signed(msgIdList);
        }
    }

    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        if (super.acceptInboundMessage(msg)) {
            Packet packet = (Packet) msg;
            return packet.getAction().equals(MessageActionEnum.SIGNED.getCode());
        }
        return false;
    }
}
