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
@Table(name = "t_im_friend_request")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequest implements Serializable {
    private static final long serialVersionUID = -2194927835882655365L;
    /**
     * ID
     */
    @Id
    @Column(length = 32)
    private String id;
    /**
     * 请求方ID
     */
    @Column(name = "`from`", nullable = false, length = 32)
    private String from;
    /**
     * 接收方ID
     */
    @Column(name = "`to`", nullable = false, length = 32)
    private String to;
    /**
     * 附加消息
     */
    private String message;
    /**
     * 状态(0.请求 1.接受 2.拒绝 3.忽略 4.过期)
     */
    @Column(nullable = false, columnDefinition="int default 0")
    private Integer status;
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
