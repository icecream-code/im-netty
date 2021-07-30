package com.iqy.im.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FriendRequestOperateStatusEnum implements ApiEnum {
    REQUEST(0, "请求"),
    PASS(1, "通过"),
    REFUSE(2, "拒绝"),
    IGNORE(3, "忽略");

    private final Integer code;
    private final String message;
}
