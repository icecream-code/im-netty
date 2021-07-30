package com.iqy.im.service;

import com.iqy.im.netty.packet.Packet;
import com.iqy.im.vo.FriendRequestVO;

public interface ChannelService {

    void send(String to, Packet packet);

    void pushMessage(String from, String to, Integer action, Object payload);

    void pushFriendRequestMessage(String from, String to, FriendRequestVO friendRequestVO);

    void pushFriendAcceptedMessage(String from, String to);
}
