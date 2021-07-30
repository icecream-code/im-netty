package com.iqy.im.service.impl;

import com.iqy.im.dao.FriendDao;
import com.iqy.im.dao.FriendRequestDao;
import com.iqy.im.dao.UserDao;
import com.iqy.im.domain.Friend;
import com.iqy.im.domain.FriendRequest;
import com.iqy.im.domain.User;
import com.iqy.im.enums.FriendRequestOperateStatusEnum;
import com.iqy.im.enums.FriendRequestStatusEnum;
import com.iqy.im.exception.CustomException;
import com.iqy.im.exception.ParameterException;
import com.iqy.im.oss.OssService;
import com.iqy.im.service.ChannelService;
import com.iqy.im.service.UserService;
import com.iqy.im.util.IdWorker;
import com.iqy.im.util.QRCodeUtil;
import com.iqy.im.vo.FriendRequestVO;
import com.iqy.im.vo.FriendSimplifyVO;
import com.iqy.im.vo.UserSimplifyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final IdWorker idWorker;

    private final UserDao userDao;

    private final FriendDao friendDao;

    private final FriendRequestDao friendRequestDao;

    private final ChannelService channelService;

    private final PasswordEncoder passwordEncoder;

    private final OssService ossService;

    public UserServiceImpl(UserDao userDao, IdWorker idWorker, FriendDao friendDao,
                           FriendRequestDao friendRequestDao, ChannelService channelService,
                           PasswordEncoder passwordEncoder, OssService ossService) {
        this.userDao = userDao;
        this.idWorker = idWorker;
        this.friendDao = friendDao;
        this.friendRequestDao = friendRequestDao;
        this.channelService = channelService;
        this.passwordEncoder = passwordEncoder;
        this.ossService = ossService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(User user) {
        if (isUsernameExist(user.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }

        String uid = idWorker.nextId() + "";
        user.setId(uid);
        user.setUsername(user.getUsername());
        user.setPassword(encodePassword(user.getPassword()));
        if (StringUtils.isEmpty(user.getNickname())) {
            user.setNickname(user.getUsername());
        } else {
            user.setNickname(user.getNickname());
        }
        user.setGender(0);

        try {
            byte[] bytes = QRCodeUtil.createQRCode("qrcode_" + uid);
            String url = ossService.upload("qrcode", uid + ".png", new ByteArrayInputStream(bytes));
            user.setQrCode(url);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("create qrcode error.");
            throw new RuntimeException("create qrcode error.");
        }

        userDao.save(user);
    }

    @Override
    public boolean isUsernameExist(String username) {
        return userDao.existsByUsername(username);
    }

    @Override
    public void update(String id, User user) {
        Optional<User> optional = userDao.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("用户" + id + "不存在");
        }
        User u = optional.get();
        u.setUsername(user.getUsername());
        u.setNickname(user.getNickname());
        u.setGender(user.getGender());
        u.setFaceImage(user.getFaceImage());
        u.setFaceImageHD(user.getFaceImageHD());
        u.setQrCode(user.getQrCode());
        u.setClientId(user.getClientId());
        userDao.save(u);
    }

    @Override
    public void updateNickname(String id, String nickname) {
        Optional<User> optional = userDao.findById(id);
        if (optional.isPresent()) {
            User u = optional.get();
            u.setNickname(nickname);
            userDao.save(u);
        }
    }

    @Override
    public void updateGender(String id, int gender) {
        User user = userDao.getOne(id);
        user.setGender(gender);
        userDao.save(user);
    }

    @Override
    public void uploadAvatar(String uid, MultipartFile file) {
        User user = userDao.getOne(uid);
        String url;
        try {
            url = ossService.upload("avatar", file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            log.error("upload avatar failed: {}", e.getLocalizedMessage());
            throw new RuntimeException("upload avatar failed.");
        }
        ossService.deleteFile(user.getFaceImage());

        user.setFaceImage(url);
        user.setFaceImageHD(url);
        userDao.save(user);
    }

    @Override
    public UserSimplifyVO getUserSimplify(String id) {
        User user = userDao.findById(id).orElseThrow(() -> new RuntimeException("User not exist."));
        return UserSimplifyVO.ofUser(user);
    }

    @Override
    public UserSimplifyVO searchForFriendRequest(String fromUserId, String username) {
        User user = userDao.findByUsername(username);
        if (null == user) {
            throw new ParameterException(FriendRequestStatusEnum.USER_NOT_EXIST);
        }
        if (user.getId().equals(fromUserId)) {
            throw new ParameterException(FriendRequestStatusEnum.IS_YOURSELF);
        }
        Friend friend = friendDao.findByUidAndAndFid(fromUserId, user.getId());
        if (null != friend) {
            throw new ParameterException(FriendRequestStatusEnum.IS_YOUR_FRIEND);
        }
        return UserSimplifyVO.ofUser(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendFriendRequest(String fromUserId, String toUserId, String message) {
        User user = userDao.findById(toUserId).orElse(null);
        if (null == user) {
            return;
        }
        Friend friend = friendDao.findByUidAndAndFid(fromUserId, toUserId);
        // 如果不是好友关系
        if (null == friend) {
            FriendRequest friendRequest = FriendRequest.builder()
                    .id(idWorker.nextId() + "")
                    .from(fromUserId)
                    .to(toUserId)
                    .message(message)
                    .status(FriendRequestOperateStatusEnum.REQUEST.getCode())
                    .createTime(new Date())
                    .build();
            friendRequestDao.save(friendRequest);

            // 使用websocket主动推送消息到接收者，更新通讯录列表为最新
            User fromUser = userDao.getOne(fromUserId);
            channelService.pushFriendRequestMessage(fromUserId, toUserId, new FriendRequestVO(fromUser, friendRequest));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void passFriendRequest(String requestUserId, String acceptUserId) {
        // 双向保存好友关系
        saveFriend(requestUserId, acceptUserId);
        saveFriend(acceptUserId, requestUserId);
        // 删除请求记录
        deleteFriendRequest(requestUserId, acceptUserId);

        // 使用websocket主动推送消息到请求发起者，更新通讯录列表为最新
        channelService.pushFriendAcceptedMessage(acceptUserId, requestUserId);
    }

    @Override
    public void deleteFriendRequest(String requestUserId, String acceptUserId) {
        friendRequestDao.deleteByFromAndTo(requestUserId, acceptUserId);
    }

    @Override
    public List<FriendRequestVO> getFriendRequestList(String acceptUserId) {
        return friendRequestDao.selectAllByUserId(acceptUserId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveFriend(String uid, String fid) {
        Friend friend = Friend.builder()
                .id(idWorker.nextId() + "")
                .uid(uid)
                .fid(fid)
                .markName("")
                .build();
        friendDao.save(friend);
    }

    @Override
    public List<FriendSimplifyVO> getFriends(String userId) {
        List<Friend> friendList = friendDao.findAllByUid(userId);

        Map<String, String> nameMap = friendList.stream()
                .collect(Collectors.toMap(Friend::getFid, Friend::getMarkName, (k1, k2) -> k2));

        List<String> fidList = friendList.stream()
                .map(Friend::getFid)
                .collect(Collectors.toList());
        fidList.add(userId);
        List<User> userList = userDao.findAllByIdIn(fidList);

        Comparator<Object> chinese = Collator.getInstance(Locale.CHINA);
        Comparator<Object> english = Collator.getInstance(Locale.ENGLISH);

        return userList.stream()
                .map(user -> FriendSimplifyVO.of(user, nameMap.getOrDefault(user.getId(), null)))
                .sorted((u1, u2) -> {
                    int c = english.compare(u1.getLetter(), u2.getLetter());
                    return c == 0 ? chinese.compare(u1.getMarkName(), u2.getMarkName()) : c;
                })
                .collect(Collectors.toList());
    }

    @Override
    public FriendSimplifyVO getFriendSimplify(String uid, String fid) {
        User user = userDao.findById(fid).orElseThrow(() -> new RuntimeException("User not exist."));
        Friend friend = friendDao.findByUidAndAndFid(uid, fid);
        if (friend != null) {
            return FriendSimplifyVO.of(user, friend.getMarkName());
        }
        return FriendSimplifyVO.of(user, null);
    }

    @Override
    public void markFriend(String uid, String fid, String mark) {
        Friend friend = friendDao.findByUidAndAndFid(uid, fid);
        friend.setMarkName(mark);
        friendDao.save(friend);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteFriend(String uid, String fid) {
        friendDao.deleteByUidAndFid(uid, fid);
        friendDao.deleteByUidAndFid(fid, uid);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
