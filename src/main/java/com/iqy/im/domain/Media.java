package com.iqy.im.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "t_im_media")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Media implements Serializable {
    private static final long serialVersionUID = -5023228218036239307L;
    /**
     * ID
     */
    @Id
    @Column(length = 32)
    private String id;
    /**
     * 动态ID
     */
    @Column(length = 32, nullable = false)
    private String mid;
    /**
     * 媒体地址
     */
    private String url;
    /**
     * 创建时间
     */
    @CreatedDate
    private Date createTime;
}
