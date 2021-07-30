package com.iqy.im.dao;

import com.iqy.im.domain.FriendRequest;
import com.iqy.im.vo.FriendRequestVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface FriendRequestDao extends JpaRepository<FriendRequest, String>, JpaSpecificationExecutor<FriendRequest> {

    List<FriendRequest> findAllByTo(String to);

    @Transactional
    int deleteByFromAndTo(String from, String to);

    @Query("SELECT new com.iqy.im.vo.FriendRequestVO(u, fr) FROM FriendRequest fr INNER JOIN User u " +
            "ON fr.from = u.id WHERE fr.to = :uid")
    List<FriendRequestVO> selectAllByUserId(@Param("uid") String uid);
}
