package com.iqy.im.dao;

import com.iqy.im.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MessageDao extends JpaRepository<Message, String>, JpaSpecificationExecutor<Message> {

    List<Message> findAllByToAndSignedOrderByCreateTimeAsc(String to, Integer signed);
}
