package com.iqy.im.dao;

import com.iqy.im.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaDao extends JpaRepository<Media, String> {
    List<Media> findAllByMid(String mid);
}
