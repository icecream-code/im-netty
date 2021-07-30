package com.iqy.im.service.impl;

import com.iqy.im.enums.MessageActionEnum;
import com.iqy.im.netty.UserChannelRel;
import com.iqy.im.netty.packet.Packet;
import com.iqy.im.service.ChannelService;
import com.iqy.im.vo.FriendRequestVO;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

@Service
public class ChannelServiceImpl implements ChannelService {

    @Override
    public void send(String to, Packet packet) {
        Channel channel = UserChannelRel.get(to);
        if (channel != null) {
            channel.writeAndFlush(packet);
        }
    }

    @Override
    public void pushMessage(String from, String to, Integer action, Object payload) {
        Packet packet = Packet.builder()
                .action(action)
                .msg(payload)
                .build();
        send(to, packet);
    }

    @Override
    public void pushFriendRequestMessage(String from, String to, FriendRequestVO friendRequestVO) {
        pushMessage(from, to, MessageActionEnum.FRIEND_REQUEST.getCode(), friendRequestVO);
    }

    @Override
    public void pushFriendAcceptedMessage(String from, String to) {
        pushMessage(from, to, MessageActionEnum.FRIEND_ACCEPTED.getCode(), null);
    }
}
