package com.iqy.im.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "t_im_message")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    private static final long serialVersionUID = -398591653512702197L;
    /**
     * ID
     */
    @Id
    @Column(length = 32)
    private String id;
    /**
     * 发送方ID
     */
    @Column(name = "`from`", nullable = false, length = 32)
    private String from;
    /**
     * 接收方ID
     */
    @Column(name = "`to`", nullable = false, length = 32)
    private String to;
    /**
     * 消息类型(0.文本 1.图片 2.语音)
     */
    @Column(nullable = false, columnDefinition="int default 0")
    private Integer type;
    /**
     * 消息内容
     */
    @Column(nullable = false)
    private String content;
    /**
     * 消息签收状态(0.未签收 1.已签收)
     */
    @Column(nullable = false, columnDefinition="int default 0")
    private Integer signed;
    /**
     * 创建时间
     */
    @CreatedDate
    private Date createTime;
    /**
     * 更新时间
     */
    @LastModifiedDate
    private Date updateTime;
}
