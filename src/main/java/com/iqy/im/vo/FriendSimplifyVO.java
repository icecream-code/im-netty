package com.iqy.im.vo;

import com.iqy.im.domain.User;
import com.iqy.im.util.ChineseCharToEnUtil;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.Locale;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendSimplifyVO {
    private String id;

    private String username;

    private String nickname;

    private String faceImage;

    private Integer gender;

    private String markName;

    private String letter;

    public static FriendSimplifyVO of(User user, String markName) {
        FriendSimplifyVO vo = FriendSimplifyVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .faceImage(user.getFaceImage())
                .gender(user.getGender())
                .build();
        if (StringUtils.isEmpty(markName)) {
            markName = user.getNickname();
        }
        vo.setMarkName(markName); // 备注名
        vo.setLetter(ChineseCharToEnUtil.getFirstLetter(markName).toUpperCase(Locale.ROOT)); // 首字母
        return vo;
    }
}
