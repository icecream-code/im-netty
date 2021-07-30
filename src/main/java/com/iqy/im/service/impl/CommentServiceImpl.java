package com.iqy.im.service.impl;

import com.iqy.im.dao.CommentDao;
import com.iqy.im.domain.Comment;
import com.iqy.im.enums.CommentTypeEnum;
import com.iqy.im.service.CommentService;
import com.iqy.im.util.IdWorker;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    private final IdWorker idWorker;

    public CommentServiceImpl(CommentDao commentDao, IdWorker idWorker) {
        this.commentDao = commentDao;
        this.idWorker = idWorker;
    }

    @Override
    public void comment(String uid, String commentId, String content) {
        Comment comment = getById(commentId);
        comment = Comment.builder()
                .id(idWorker.nextId() + "")
                .pid(commentId)
                .mid(comment.getMid())
                .uid(uid)
                .type(CommentTypeEnum.COMMENT.getCode())
                .content(content)
                .build();
        // 保存点赞
        commentDao.save(comment);
    }

    @Override
    public void like(String uid, String commentId) {
        Comment comment = commentDao.findByUidAndPidAndType(uid, commentId, CommentTypeEnum.LIKE.getCode());

        // 判断是否已经对评论进行点赞
        if (null != comment) {
            throw new RuntimeException("Duplicate like");
        }

        // 获取并判断评论是否存在
        comment = getById(commentId);
        // 构建点赞对象
        comment = Comment.builder()
                .id(idWorker.nextId() + "")
                .mid(comment.getMid())
                .uid(uid)
                .type(CommentTypeEnum.LIKE.getCode())
                .build();
        // 保存点赞
        commentDao.save(comment);
    }

    @Override
    public void unlike(String uid, String commentId) {
        Comment comment = commentDao.findByUidAndPidAndType(uid, commentId, CommentTypeEnum.LIKE.getCode());
        if (null != comment) {
            commentDao.delete(comment);
        }
    }

    @Override
    public Comment getById(String id) {
        Optional<Comment> optional = commentDao.findById(id);
        optional.orElseThrow(() -> new RuntimeException("Comment not found"));
        return optional.get();
    }
}
