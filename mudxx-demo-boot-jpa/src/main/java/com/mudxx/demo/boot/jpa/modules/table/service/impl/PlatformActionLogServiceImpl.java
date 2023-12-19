package com.mudxx.demo.boot.jpa.modules.table.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mudxx.demo.boot.jpa.modules.table.dao.PlatformActionLog;
import com.mudxx.demo.boot.jpa.modules.table.dao.mapper.PlatformActionLogRepository;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.PlatformActionLogForm;
import com.mudxx.demo.boot.jpa.modules.table.service.IPlatformActionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class PlatformActionLogServiceImpl implements IPlatformActionLogService {

    @Autowired
    private PlatformActionLogRepository platformActionLogRepository;

    @Override
    public PlatformActionLog findById(Long id) {
        Optional<PlatformActionLog> optional = platformActionLogRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void add(PlatformActionLogForm logForm) {
        PlatformActionLog log = new PlatformActionLog();
        BeanUtil.copyProperties(logForm, log);
        platformActionLogRepository.save(log);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void update(Long id, String uri) {
        platformActionLogRepository.update(id, uri);
    }

}
