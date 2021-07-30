package com.iqy.im.netty.handler;

import com.iqy.im.netty.attribute.Attributes;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
@ChannelHandler.Sharable
public class AuthorizationHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String uid = ctx.channel().attr(Attributes.USER_ID).get();
        if (StringUtils.isEmpty(uid)) {
            log.debug("user {} not login.", uid);
            ctx.channel().close();
        } else {
            log.debug("user {} has login.", uid);
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }
}
