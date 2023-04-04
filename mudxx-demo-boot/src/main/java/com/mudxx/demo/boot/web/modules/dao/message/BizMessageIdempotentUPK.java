package com.mudxx.demo.boot.web.modules.dao.message;


import lombok.Data;

import java.io.Serializable;

/**
 * @author laiwen
 */
@Data
public class BizMessageIdempotentUPK implements Serializable {
    private static final long serialVersionUID = -6437202781273487837L;
    private String applicationName;

    private String topic;

    private String tags;

    private String msgUniqKey;

}
