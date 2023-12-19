package com.mudxx.demo.boot.jpa.modules.table.dao.mapper;

import com.mudxx.demo.boot.jpa.modules.table.dao.SysDictData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysDictDataRepository extends JpaRepository<SysDictData, String>, JpaSpecificationExecutor<SysDictData> {

    List<SysDictData> findBySysDictCodeId(String sysDictCodeId);

    Integer countBySysDictCodeId(String sysDictCodeId);

    @Query(value = "select dc.code, dd.name, dd.name_en, dd.value " +
            "from sys_dict_data dd " +
            "inner join sys_dict_code dc on dc.id = dd.sys_dict_code_id " +
            "and dc.is_del = 0 " +
            "and dc.status = 1 " +
            "and dd.status = 1 ", nativeQuery = true)
    List<Map<String, String>> findUsableData();
}
