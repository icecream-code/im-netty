package com.iqy.im.vo;

import com.iqy.im.domain.FriendRequest;
import com.iqy.im.domain.User;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestVO {

    private String id;

    private String from;

    private String to;

    private String message;

    private Integer status;

    private Date createTime;

    private String username;

    private String nickname;

    private String faceImage;

    public FriendRequestVO(User user, FriendRequest friendRequest) {
        id = friendRequest.getId();
        from = friendRequest.getFrom();
        to = friendRequest.getTo();
        message = friendRequest.getMessage();
        status = friendRequest.getStatus();
        createTime = friendRequest.getCreateTime();
        username = user.getUsername();
        nickname = user.getNickname();
        faceImage = user.getFaceImage();
    }
}
