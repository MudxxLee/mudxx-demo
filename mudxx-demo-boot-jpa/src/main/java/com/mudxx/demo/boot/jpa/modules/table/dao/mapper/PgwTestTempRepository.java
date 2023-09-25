package com.mudxx.demo.boot.jpa.modules.table.dao.mapper;

import com.mudxx.demo.boot.jpa.modules.table.dao.PgwTestTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PgwTestTempRepository extends JpaRepository<PgwTestTemp, String> {

    @Query(value = "select * from payment_system.pgw_test_temp ", nativeQuery = true)
    List<Map<String, Object>> findList();

}

