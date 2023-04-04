package com.mudxx.demo.boot.quartz.database.dao.mapper;

import com.mudxx.demo.boot.quartz.database.dao.QrtzTriggers;
import com.mudxx.demo.boot.quartz.database.dao.upk.QrtzTriggersUPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QrtzTriggersRepository extends JpaRepository<QrtzTriggers, QrtzTriggersUPK>, JpaSpecificationExecutor<QrtzTriggers> {
    
}
