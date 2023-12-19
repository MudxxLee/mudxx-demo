package com.mudxx.demo.boot.jpa.modules.table.service;

import com.mudxx.demo.boot.jpa.modules.table.dao.PlatformActionLog;
import com.mudxx.demo.boot.jpa.modules.table.service.dto.PlatformActionLogForm;

public interface IPlatformActionLogService {

    PlatformActionLog findById(Long id);

    void add(PlatformActionLogForm logForm);

    void update(Long id, String uri);

}
