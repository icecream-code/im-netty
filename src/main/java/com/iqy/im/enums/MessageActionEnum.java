package com.iqy.im.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageActionEnum implements ApiEnum {
    CONNECT(0, "初始化（重新）连接"),
    KEEP_ALIVE(1, "客户端保持心跳"),
    CHAT(2, "聊天消息"),
    SIGNED(3, "消息签收"),
    FRIEND_REQUEST(4, "好友请求"),
    FRIEND_ACCEPTED(5, "好友通过");

    private final Integer code;
    private final String message;
}
