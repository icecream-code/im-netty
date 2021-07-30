package com.iqy.im.service.impl;

import com.iqy.im.dao.CommentDao;
import com.iqy.im.dao.MediaDao;
import com.iqy.im.dao.MomentDao;
import com.iqy.im.domain.Comment;
import com.iqy.im.domain.Media;
import com.iqy.im.domain.Moment;
import com.iqy.im.dto.CommentDTO;
import com.iqy.im.dto.MomentDTO;
import com.iqy.im.enums.CommentTypeEnum;
import com.iqy.im.service.MomentService;
import com.iqy.im.util.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MomentServiceImpl implements MomentService {
    private final MomentDao momentDao;

    private final MediaDao mediaDao;

    private final CommentDao commentDao;

    private final IdWorker idWorker;

    public MomentServiceImpl(MomentDao momentDao, MediaDao mediaDao, CommentDao commentDao, IdWorker idWorker) {
        this.momentDao = momentDao;
        this.mediaDao = mediaDao;
        this.commentDao = commentDao;
        this.idWorker = idWorker;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(String uid, MomentDTO s) {
        String id = createId();
        Moment moment = Moment.builder()
                .id(id)
                .uid(uid)
                .text(s.getText())
                .longitude(s.getLongitude())
                .latitude(s.getLatitude())
                .location(s.getLocation())
                .visibility(s.getVisibility())
                .fidList(s.getFidList())
                .build();
        momentDao.save(moment);

        // 媒体
        List<String> medias = s.getMedias();
        if (null != medias && medias.size() > 0) {
            List<Media> mediaList = medias.stream().map(m -> Media.builder()
                    .id(createId())
                    .mid(id)
                    .url(m)
                    .build()
            ).collect(Collectors.toList());
            mediaDao.saveAll(mediaList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String uid, String id) {
        Moment moment = getById(id);
        if (uid.equals(moment.getUid())) {
            // 删除媒体
            List<Media> mediaList = mediaDao.findAllByMid(id);
            mediaDao.deleteAll(mediaList);
            // 删除评论
            List<Comment> commentList = commentDao.findAllByMid(id);
            commentDao.deleteAll(commentList);
            // 删除动态
            momentDao.delete(moment);
        }
    }

    @Override
    public Moment getById(String id) {
        Optional<Moment> optional = momentDao.findById(id);
        optional.orElseThrow(() -> new RuntimeException("Moment not found"));
        return optional.get();
    }

    @Override
    public List<CommentDTO> getComments(String momentId) {
        return commentDao.selectAllByMid(momentId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void comment(String uid, String momentId, String content) {
        Moment moment = getById(momentId);
        Comment comment = Comment.builder()
                .id(createId())
                .uid(uid)
                .mid(moment.getId())
                .type(CommentTypeEnum.COMMENT.getCode())
                .content(content)
                .build();
        commentDao.save(comment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void like(String uid, String momentId) {
        // 获取并检查动态是否存在
        Moment moment = getById(momentId);

        Comment comment = commentDao.findByUidAndMidAndType(uid, moment.getId(), CommentTypeEnum.LIKE.getCode());
        if (null != comment) {
            throw new RuntimeException("Duplicate like");
        }

        comment = Comment.builder()
                .id(createId())
                .mid(momentId)
                .uid(uid)
                .type(CommentTypeEnum.LIKE.getCode())
                .build();
        // 保存点赞
        commentDao.save(comment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unlike(String uid, String momentId) {
        Moment moment = getById(momentId);
        commentDao.deleteByUidAndMidAndTypeAndPidIsNull(uid, moment.getId(), CommentTypeEnum.LIKE.getCode());
    }

    private String createId() {
        return idWorker.nextId() + "";
    }
}
