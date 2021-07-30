package com.iqy.im.service;

import com.iqy.im.domain.Message;
import com.iqy.im.netty.packet.Msg;

import java.util.List;

public interface MessageService {

    Message save(Msg msg);

    void signed(List<String> idList);

    List<Message> getUnSignedMessages(String to);
}
