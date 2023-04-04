package com.mudxx.demo.boot.quartz.database.service;

import com.mudxx.common.web.response.CommonPage;
import com.mudxx.demo.boot.quartz.database.service.bo.QrtzJobDetailsBo;

public interface IQuartzQueryService {

    CommonPage<QrtzJobDetailsBo> selectPageJobDetails(Integer pageNum, Integer pageSize);

}
