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
@Table(name = "t_im_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -2212545281667782263L;

    /**
     * ID
     */
    @Id
    @Column(length = 32)
    private String id;
    /**
     * 用户名/账号
     */
    @Column(unique = true, nullable = false, length = 32)
    private String username;
    /**
     * 密码
     */
    @Column(nullable = false, length = 64)
    private String password;
    /**
     * 昵称
     */
    @Column(nullable = false, length = 32)
    private String nickname;
    /**
     * 性别(0.女 1.男)
     */
    @Column(nullable = false, columnDefinition="int default 0")
    private Integer gender;
    /**
     * 头像
     */
    private String faceImage;
    /**
     * 高清头像
     */
    private String faceImageHD;
    /**
     * 二维码
     */
    private String qrCode;
    /**
     * client ID
     */
    @Column(length = 64)
    private String clientId;
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
