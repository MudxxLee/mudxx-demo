package com.mudxx.demo.boot.jpa.modules.table.service;

import com.mudxx.demo.boot.jpa.modules.table.dao.PgwTestTemp;

import java.util.List;
import java.util.Map;

public interface IPgwTestTempService {

    PgwTestTemp findById(String id);

    List<Map<String, Object>> findList();

}
