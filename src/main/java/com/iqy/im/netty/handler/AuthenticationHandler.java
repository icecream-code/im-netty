package com.iqy.im.netty.handler;

import com.iqy.im.enums.MessageActionEnum;
import com.iqy.im.netty.UserChannelRel;
import com.iqy.im.netty.attribute.Attributes;
import com.iqy.im.netty.packet.Packet;
import com.iqy.im.security.JwtUtil;
import com.iqy.im.util.SpringContextUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 认证处理器
 */
@Slf4j
@ChannelHandler.Sharable
public class AuthenticationHandler extends SimpleChannelInboundHandler<Packet> {

    private final JwtUtil jwtUtil;

    public AuthenticationHandler() {
        jwtUtil = SpringContextUtil.getBean(JwtUtil.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {
        // get token from expand field
        String token = packet.getExpand();
        if (!StringUtils.isEmpty(token)) {
            // validate token
            if (jwtUtil.verify(token)) {
                String uid = jwtUtil.getAuthUser(token).getId();

                UserChannelRel.put(uid, ctx.channel());
                ctx.channel().attr(Attributes.USER_ID).set(uid);
                log.debug("user {} connected.", uid);
            } else {
                ctx.channel().close();
            }
        } else {
            ctx.channel().close();
        }
    }

    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        if (super.acceptInboundMessage(msg)) {
            Packet packet = (Packet) msg;
            return packet.getAction().equals(MessageActionEnum.CONNECT.getCode());
        }
        return false;
    }
}
