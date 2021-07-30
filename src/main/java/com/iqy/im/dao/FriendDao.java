package com.iqy.im.dao;

import com.iqy.im.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FriendDao extends JpaRepository<Friend, String>, JpaSpecificationExecutor<Friend> {

    List<Friend> findAllByUid(String uid);

    Friend findByUidAndAndFid(String uid, String fid);

    void deleteByUidAndFid(String uid, String fid);
}
