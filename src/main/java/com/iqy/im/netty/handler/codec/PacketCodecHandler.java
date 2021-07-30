package com.iqy.im.netty.handler.codec;

import com.iqy.im.netty.packet.Packet;
import com.iqy.im.util.JsonUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<TextWebSocketFrame, Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> list) throws Exception {
        String json = JsonUtil.objectToJson(packet);
        list.add(new TextWebSocketFrame(json));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame, List<Object> list) throws Exception {
        String text = textWebSocketFrame.text();
        Packet packet = JsonUtil.jsonToObject(text, Packet.class);
        if (null != packet) {
            list.add(packet);
        }
    }
}
