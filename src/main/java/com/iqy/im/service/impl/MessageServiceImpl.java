package com.iqy.im.service.impl;

import com.iqy.im.dao.MessageDao;
import com.iqy.im.domain.Message;
import com.iqy.im.enums.MessageSignEnum;
import com.iqy.im.netty.packet.Msg;
import com.iqy.im.service.MessageService;
import com.iqy.im.util.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageDao messageDao;

    private final IdWorker idWorker;

    public MessageServiceImpl(MessageDao messageDao, IdWorker idWorker) {
        this.messageDao = messageDao;
        this.idWorker = idWorker;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Message save(Msg msg) {
        String id = idWorker.nextId() + "";
        Message message = Message.builder()
                .id(id)
                .from(msg.getFrom())
                .to(msg.getTo())
                .type(msg.getType())
                .content(msg.getContent())
                .signed(MessageSignEnum.UNSIGNED.getCode())
                .build();
        return messageDao.save(message);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void signed(List<String> idList) {
        List<Message> list = messageDao.findAllById(idList);
        if (idList.size() > list.size()) {
            throw new RuntimeException("签收消息ID不合法");
        }
        list.forEach(message -> message.setSigned(MessageSignEnum.SIGNED.getCode()));
        messageDao.saveAll(list);
    }

    @Override
    public List<Message> getUnSignedMessages(String to) {
        return messageDao.findAllByToAndSignedOrderByCreateTimeAsc(to, MessageSignEnum.UNSIGNED.getCode());
    }
}
