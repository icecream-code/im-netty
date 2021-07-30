package com.iqy.im.dao;

import com.iqy.im.domain.Moment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MomentDao extends JpaRepository<Moment, String>, JpaSpecificationExecutor<Moment> {
}
