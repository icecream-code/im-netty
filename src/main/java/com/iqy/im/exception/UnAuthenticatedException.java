package com.iqy.im.exception;

public class UnAuthenticatedException extends HttpException {
    public UnAuthenticatedException (int code) {
        this.code = code;
        this.httpStatus = 401;
    }
}
