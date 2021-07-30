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
@Table(name = "t_im_friend")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Friend implements Serializable {
    private static final long serialVersionUID = 3607878647919581143L;
    /**
     * ID
     */
    @Id
    @Column(length = 32)
    private String id;
    /**
     * 用户ID
     */
    @Column(nullable = false, length = 32)
    private String uid;
    /**
     * 好友ID
     */
    @Column(nullable = false, length = 32)
    private String fid;
    /**
     * 备注名
     */
    @Column(columnDefinition="varchar(128) default ''")
    private String markName;
    /**
     * 创建时间
     */
    @CreatedDate
    private Date createTime;
}
