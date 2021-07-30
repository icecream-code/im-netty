package com.iqy.im.service;

import com.iqy.im.domain.User;
import com.iqy.im.vo.FriendRequestVO;
import com.iqy.im.vo.FriendSimplifyVO;
import com.iqy.im.vo.UserSimplifyVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    /**
     * 用户注册
     *
     * @param user 用户信息
     */
    void register(User user);

    boolean isUsernameExist(String username);

    void update(String id, User user);

    void updateNickname(String id, String nickname);

    void updateGender(String id, int gender);

    void uploadAvatar(String uid, MultipartFile file);

    UserSimplifyVO getUserSimplify(String id);

    UserSimplifyVO searchForFriendRequest(String fromUserId, String username);

    List<FriendRequestVO> getFriendRequestList(String acceptUserId);

    void sendFriendRequest(String fromUserId, String toUserId, String message);

    void passFriendRequest(String requestUserId, String acceptUserId);

    void deleteFriendRequest(String requestUserId, String acceptUserId);

    void saveFriend(String uid, String fid);

    List<FriendSimplifyVO> getFriends(String userId);

    FriendSimplifyVO getFriendSimplify(String uid, String fid);

    void markFriend(String uid, String fid, String mark);

    void deleteFriend(String uid, String fid);
}
