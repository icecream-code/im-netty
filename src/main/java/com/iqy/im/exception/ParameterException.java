package com.iqy.im.exception;

import com.iqy.im.enums.ApiEnum;

public class ParameterException extends HttpException {
    public ParameterException (int code) {
        super(code, 400);
    }

    public ParameterException (int code, String message) {
        super(code, 400, message);
    }

    public ParameterException(ApiEnum apiEnum) {
        this(apiEnum.getCode(), apiEnum.getMessage());
    }
}
