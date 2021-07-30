package com.iqy.im.netty.packet;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Packet implements Serializable {
    private static final long serialVersionUID = -236030280433539144L;

    private Integer action;

    private String expand;

    private Object msg;
}
