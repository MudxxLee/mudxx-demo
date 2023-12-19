package com.mudxx.demo.boot.jpa.modules.table.dao.mapper;

import com.mudxx.demo.boot.jpa.modules.table.dao.SysDictCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDictCodeRepository extends JpaRepository<SysDictCode, String>, JpaSpecificationExecutor<SysDictCode> {

    SysDictCode findByCode(String code);

    List<SysDictCode> findByPidAndIsDel(String pid, Integer isDel);

    Integer countByPidAndIsDel(String pid, Integer isDel);

}
