package com.mudxx.demo.boot.quartz.database.dao.mapper;

import com.mudxx.demo.boot.quartz.database.dao.QrtzJobDetails;
import com.mudxx.demo.boot.quartz.database.dao.upk.QrtzJobDetailsUPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QrtzJobDetailsRepository extends JpaRepository<QrtzJobDetails, QrtzJobDetailsUPK>, JpaSpecificationExecutor<QrtzJobDetails> {

}
