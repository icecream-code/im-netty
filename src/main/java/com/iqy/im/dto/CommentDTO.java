package com.iqy.im.dto;

import com.iqy.im.domain.Comment;
import com.iqy.im.domain.User;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private String id;

    private String pid;

    private String mid;

    private String uid;

    private Integer type;

    private String content;

    private Date createTime;

    private String nickname;

    private String faceImage;

    public CommentDTO(Comment comment) {
        id = comment.getId();
        pid = comment.getPid();
        mid = comment.getMid();
        uid = comment.getUid();
        type = comment.getType();
        content = comment.getContent();
        createTime = comment.getCreateTime();
    }

    public CommentDTO(Comment comment, User user) {
        this(comment);
        this.nickname = user.getNickname();
        this.faceImage = user.getFaceImage();
    }
}
