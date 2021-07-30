package com.iqy.im.service;

import com.iqy.im.domain.Moment;
import com.iqy.im.dto.CommentDTO;
import com.iqy.im.dto.MomentDTO;

import java.util.List;

public interface MomentService {

    void save(String uid, MomentDTO s);

    void delete(String uid, String id);

    Moment getById(String id);

    List<CommentDTO> getComments(String momentId);

    void comment(String uid, String momentId, String content);

    void like(String uid, String momentId);

    void unlike(String uid, String momentId);
}
