package com.iqy.im.exception;

public class ForbiddenException extends HttpException {
    public ForbiddenException(int code) {
        this.code = code;
        this.httpStatus = 403;
    }
}
