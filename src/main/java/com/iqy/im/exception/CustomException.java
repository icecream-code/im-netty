package com.iqy.im.exception;

import com.iqy.im.enums.ApiEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 自定义异常类
 */
@Getter
@NoArgsConstructor
public class CustomException extends RuntimeException {

    private Integer code;

    public CustomException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(ApiEnum apiEnum) {
        this(apiEnum.getCode(), apiEnum.getMessage());
    }

}
