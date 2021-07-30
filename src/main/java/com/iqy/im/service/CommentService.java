package com.iqy.im.service;

import com.iqy.im.domain.Comment;

public interface CommentService {

    /**
     * 回复评论
     *
     * @param uid 用户ID
     * @param commentId 评论ID
     * @param content 评论内容
     */
    void comment(String uid, String commentId, String content);

    /**
     * 对评论进行点赞
     *
     * @param uid 用户ID
     * @param commentId 评论ID
     */
    void like(String uid, String commentId);

    /**
     * 取消对评论的点赞
     *
     * @param uid 用户ID
     * @param commentId 评论ID
     */
    void unlike(String uid, String commentId);

    /**
     * 根据ID获取，如果不存在抛出异常
     *
     * @param id ID
     * @return Comment
     */
    Comment getById(String id);
}
