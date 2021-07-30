package com.iqy.im.vo;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HttpResult {

    private Integer code;

    private String message;

    private String request;
}
