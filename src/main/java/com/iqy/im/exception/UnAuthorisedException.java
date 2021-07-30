package com.iqy.im.exception;

public class UnAuthorisedException extends HttpException {
    public UnAuthorisedException(int code) {
        this.code = code;
        this.httpStatus = 403;
    }
}
