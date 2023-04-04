package com.mudxx.demo.boot.web.modules.service.message.bo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author laiwen
 */
@Data
public class BizMessageIdempotentBo implements Serializable {
    private static final long serialVersionUID = -6437202781273487837L;
    private String applicationName;

    private String topic;

    private String tags;

    private String msgUniqKey;

    private Integer status;

    private Date expireTime;

}
