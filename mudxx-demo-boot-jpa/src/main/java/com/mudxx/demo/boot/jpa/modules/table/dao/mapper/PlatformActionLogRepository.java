package com.mudxx.demo.boot.jpa.modules.table.dao.mapper;

import com.mudxx.demo.boot.jpa.modules.table.dao.PlatformActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PlatformActionLogRepository extends JpaRepository<PlatformActionLog, Long> {

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "update platform_action_log set uri = :uri where id = :id ", nativeQuery = true)
    int update(@Param("id") Long id, @Param("uri") String uri);

}

