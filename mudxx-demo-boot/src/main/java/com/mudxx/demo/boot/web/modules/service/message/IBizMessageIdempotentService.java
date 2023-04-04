package com.mudxx.demo.boot.web.modules.service.message;

import com.mudxx.demo.boot.web.modules.service.message.bo.BizMessageIdempotentBo;

/**
 * @author laiw
 * @date 2023/4/3 15:42
 */
public interface IBizMessageIdempotentService {

    BizMessageIdempotentBo findByMsgUniqKey(String msgUniqKey);

}
