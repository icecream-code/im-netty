package com.iqy.im.advice;

import com.iqy.im.component.HttpCodeConfiguration;
import com.iqy.im.exception.HttpException;
import com.iqy.im.vo.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {
    private final HttpCodeConfiguration httpCodeConfiguration;

    @Autowired
    public GlobalExceptionAdvice(HttpCodeConfiguration httpCodeConfiguration) {
        this.httpCodeConfiguration = httpCodeConfiguration;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpResult handleException(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        String req = request.getMethod() + " " + request.getRequestURI();
        return new HttpResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error", req);
    }

    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity<HttpResult> handleException(HttpServletRequest request, HttpException e) {
        String req = request.getMethod() + " " + request.getRequestURI();

        String message = e.getMessage();
        if (StringUtils.isEmpty(message)) {
            message = httpCodeConfiguration.getMessage(e.getCode());
        }

        HttpResult httpResult = new HttpResult(e.getCode(), message, req);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatus());
        if (null == httpStatus) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(httpResult, httpHeaders, httpStatus);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResult handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        String req = request.getMethod() + " " + request.getRequestURI();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        StringBuffer stringBuffer = new StringBuffer();
        errors.forEach(error -> stringBuffer.append(error.getDefaultMessage()).append(";"));
        return new HttpResult(HttpStatus.BAD_REQUEST.value(), stringBuffer.toString(), req);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResult ConstraintViolationException(HttpServletRequest request, ConstraintViolationException e) {
        String req = request.getMethod() + " " + request.getRequestURI();
        return new HttpResult(HttpStatus.BAD_REQUEST.value(), e.getMessage(), req);
    }
}
