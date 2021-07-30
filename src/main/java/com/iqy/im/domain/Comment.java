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
@Table(name = "t_im_comment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
    private static final long serialVersionUID = 8208918544990747144L;
    /**
     * ID
     */
    @Id
    @Column(length = 32)
    private String id;
    /**
     * 回复评论ID
     */
    @Column(length = 32)
    private String pid;
    /**
     * 动态ID
     */
    @Column(length = 32, nullable = false)
    private String mid;
    /**
     * 用户ID（评论人）
     */
    private String uid;
    /**
     * 评论类型(0.点赞 1.评论)
     */
    private Integer type;
    /**
     * 内容
     */
    private String content;
    /**
     * 创建时间
     */
    @CreatedDate
    private Date createTime;
}
