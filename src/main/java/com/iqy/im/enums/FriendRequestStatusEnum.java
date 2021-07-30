package com.iqy.im.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FriendRequestStatusEnum implements ApiEnum {
    SUCCESS(0, "OK"),
    USER_NOT_EXIST(1, "该用户不存在"),
    IS_YOURSELF(2, "该用户是你自己"),
    IS_YOUR_FRIEND(3, "该用户已经是你朋友");

    private final Integer code;
    private final String message;
}
