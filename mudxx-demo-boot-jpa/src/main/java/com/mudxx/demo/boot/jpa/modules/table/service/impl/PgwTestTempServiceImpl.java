package com.mudxx.demo.boot.jpa.modules.table.service.impl;

import com.mudxx.demo.boot.jpa.modules.table.dao.PgwTestTemp;
import com.mudxx.demo.boot.jpa.modules.table.dao.mapper.PgwTestTempRepository;
import com.mudxx.demo.boot.jpa.modules.table.service.IPgwTestTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PgwTestTempServiceImpl implements IPgwTestTempService {

    @Autowired
    private PgwTestTempRepository pgwTestTempRepository;

    @Override
    public PgwTestTemp findById(String id) {
        Optional<PgwTestTemp> optional = pgwTestTempRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<Map<String, Object>> findList() {
        List<Map<String, Object>> list = pgwTestTempRepository.findList();
        return list;
    }
}
