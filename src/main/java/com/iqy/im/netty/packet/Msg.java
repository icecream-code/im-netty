package com.iqy.im.netty.packet;

import com.iqy.im.domain.Message;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Msg implements Serializable {
    private static final long serialVersionUID = 6185201527669671374L;
    /**
     * 消息ID
     */
    private String id;
    /**
     * 发送ID
     */
    private String from;
    /**
     * 接收ID
     */
    private String to;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息内容(0.文本 1.图片 2.语音)
     */
    private Integer type;
    /**
     * 聊天类型(0.单聊 1.群聊)
     */
    private Integer chatType;
}
