package com.mudxx.demo.boot.web.modules.dao.message.mapper;

import com.mudxx.demo.boot.web.modules.dao.message.BizMessageIdempotent;
import com.mudxx.demo.boot.web.modules.dao.message.BizMessageIdempotentUPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author laiw
 * @date 2023/4/3 15:25
 */
@Repository
public interface BizMessageIdempotentRepository extends JpaRepository<BizMessageIdempotent, BizMessageIdempotentUPK> {

    BizMessageIdempotent findByMsgUniqKey(String msgUniqKey);

}
