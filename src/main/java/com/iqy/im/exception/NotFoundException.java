package com.iqy.im.exception;

public class NotFoundException extends HttpException {
    public NotFoundException (int code) {
        this.code = code;
        this.httpStatus = 404;
    }
}
