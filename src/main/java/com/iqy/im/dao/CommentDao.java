package com.iqy.im.dao;

import com.iqy.im.domain.Comment;
import com.iqy.im.dto.CommentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment, String>, JpaSpecificationExecutor<Comment> {

    List<Comment> findAllByMid(String mid);

    @Query("SELECT new com.iqy.im.dto.CommentDTO(c, u) FROM Comment c INNER JOIN User u " +
            "ON c.uid = u.id WHERE c.mid = :mid")
    List<CommentDTO> selectAllByMid(@Param("mid") String mid);

    Comment findByUidAndMidAndType(String uid, String mid, Integer type);

    Comment findByUidAndPidAndType(String uid, String pid, Integer type);

    void deleteByUidAndMidAndTypeAndPidIsNull(String uid, String mid, Integer type);
}
