package com.iqy.im.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HttpException extends RuntimeException {
    protected Integer code;

    protected Integer httpStatus;

    public HttpException(Integer code) {
        this.code = code;
        this.httpStatus = 500;
    }

    public HttpException(Integer code, Integer httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public HttpException(Integer code, Integer httpStatus, String message) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
