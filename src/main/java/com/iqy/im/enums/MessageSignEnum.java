package com.iqy.im.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageSignEnum implements ApiEnum {
    UNSIGNED(0, "未签收"),
    SIGNED(1, "已签收");

    private Integer code;
    private String message;
}
