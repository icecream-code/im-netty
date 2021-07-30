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
@Table(name = "t_im_moment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Moment implements Serializable {
    private static final long serialVersionUID = 1878408875586436752L;

    /**
     * ID
     */
    @Id
    @Column(length = 32)
    private String id;
    /**
     * 用户ID
     */
    @Column(length = 32, nullable = false)
    private String uid;
    /**
     * 动态内容
     */
    private String text;
    /**
     * 可见范围(0.公开 1.私密 2.部分可见 3.不给谁看)
     */
    private Integer visibility;
    /**
     * 部分可见或谁不可见列表
     */
    private String fidList;
    /**
     * 精度
     */
    private Double longitude;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 位置
     */
    private String location;
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
