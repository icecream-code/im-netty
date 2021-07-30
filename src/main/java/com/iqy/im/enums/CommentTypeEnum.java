package com.iqy.im.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentTypeEnum implements ApiEnum {
    LIKE(0, "点赞"),
    COMMENT(1, "评论");

    private Integer code;
    private String message;
}
