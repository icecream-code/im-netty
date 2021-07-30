package com.iqy.im.vo;

import com.iqy.im.domain.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSimplifyVO {

    private String id;

    private String username;

    private String nickname;

    private Integer gender;

    private String faceImage;

    private String faceImageHD;

    private String qrCode;

    public static UserSimplifyVO ofUser(User user) {
        return UserSimplifyVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .gender(user.getGender())
                .faceImage(user.getFaceImage())
                .faceImageHD(user.getFaceImageHD())
                .qrCode(user.getQrCode())
                .build();
    }
}
