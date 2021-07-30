package com.iqy.im.controller;

import com.iqy.im.domain.FriendRequest;
import com.iqy.im.domain.Message;
import com.iqy.im.domain.User;
import com.iqy.im.enums.FriendRequestOperateStatusEnum;
import com.iqy.im.service.MessageService;
import com.iqy.im.service.UserService;
import com.iqy.im.vo.FriendRequestVO;
import com.iqy.im.vo.FriendSimplifyVO;
import com.iqy.im.vo.UserSimplifyVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController extends BaseController {

    private final UserService userService;

    private final MessageService messageService;

    public UserController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    /**
     * 用户注册
     *
     * @param user 注册信息
     */
    @PostMapping("/register")
    public void register(@RequestBody User user) {
        userService.register(user);
    }

    /**
     * 获取个人基本信息
     *
     * @return 个人基本信息
     */
    @PostMapping("/profile")
    public UserSimplifyVO profile() {
        return userService.getUserSimplify(getUserId());
    }

    @PostMapping("/upload/avatar")
    public void uploadAvatar(@RequestParam("file") MultipartFile file) {
        String uid = getUserId();
        userService.uploadAvatar(uid, file);
    }

    @GetMapping("/{id}")
    public UserSimplifyVO getUserSimplify(@PathVariable(name = "id") String id) {
        return userService.getUserSimplify(id);
    }

    /**
     * 修改昵称
     *
     * @param nickname 昵称
     */
    @PutMapping("/nickname/{nickname}")
    public void updateNickname(@PathVariable(name = "nickname") String nickname) {
        String uid = getUserId();
        userService.updateNickname(uid, nickname);
    }

    /**
     * 修改性别
     *
     * @param gender 昵称
     */
    @PutMapping("/gender/{gender}")
    public void updateGender(@PathVariable(name = "gender") int gender) {
        String uid = getUserId();
        userService.updateGender(uid, gender);
    }

    /**
     * 搜索要添加的用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @GetMapping("/username/{username}")
    public UserSimplifyVO search(@PathVariable(name = "username") String username) {
        String uid = getUserId();
        return userService.searchForFriendRequest(uid, username);
    }

    /**
     * 获取好友基本信息
     *
     * @param fid 好友ID
     * @return 好友基本信息
     */
    @GetMapping("/friend/{fid}")
    public FriendSimplifyVO getFriendSimplify(@PathVariable(name = "fid") String fid) {
        String uid = getUserId();
        return userService.getFriendSimplify(uid, fid);
    }

    /**
     * 获取好友列表
     *
     * @return 用户好友列表
     */
    @GetMapping("/friend")
    public List<FriendSimplifyVO> friendList() {
        String uid = getUserId();
        return userService.getFriends(uid);
    }

    /**
     * 获取好友请求列表
     *
     * @return 好友请求列表
     */
    @GetMapping("/friend/request")
    public List<FriendRequestVO> friendRequestList() {
        String uid = getUserId();
        return userService.getFriendRequestList(uid);
    }

    @PostMapping("/friend/request")
    public void friendRequest(@RequestBody FriendRequest friendRequest) {
        String uid = getUserId();
        userService.sendFriendRequest(uid, friendRequest.getTo(), friendRequest.getMessage());
    }

    /**
     * 好友请求处理
     *
     * @param from 请求方ID
     * @param type 处理类型
     */
    @PostMapping("/friend/request/{from}/{type}")
    public void friendRequestHandle(@PathVariable(name = "from") String from,
                                    @PathVariable(name = "type") Integer type) {
        String uid = getUserId();
        if (type.equals(FriendRequestOperateStatusEnum.PASS.getCode())) {
            userService.passFriendRequest(from, uid);
        } else if (type.equals(FriendRequestOperateStatusEnum.IGNORE.getCode())
                || type.equals(FriendRequestOperateStatusEnum.REFUSE.getCode())) {
            userService.deleteFriendRequest(from, uid);
        }
    }

    @PutMapping("/friend/{fid}/mark/{mark}")
    public void markFriend(@PathVariable(name = "fid") String fid,
                           @PathVariable(name = "mark") String mark) {
        String uid = getUserId();
        userService.markFriend(uid, fid, mark);
    }

    @DeleteMapping("/friend/{fid}")
    public void deleteFriend(@PathVariable(name = "fid") String fid) {
        String uid = getUserId();
        userService.deleteFriend(uid, fid);
    }

    /**
     * 获取未读消息
     *
     * @return 用户的未读消息集合
     */
    @GetMapping("/message/unsigned")
    public List<Message> unsignedMessageList() {
        String uid = getUserId();
        return messageService.getUnSignedMessages(uid);
    }

}
