package com.mudxx.demo.boot.web.modules.dao.message;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author laiwen
 */
@Data
@Entity
@IdClass(BizMessageIdempotentUPK.class)
@Table(name = "biz_message_idempotent")
public class BizMessageIdempotent implements Serializable {
    private static final long serialVersionUID = -6437202781273487837L;
    @Id
    @Column(name = "application_name")
    private String applicationName;

    @Id
    @Column(name = "topic")
    private String topic;

    @Id
    @Column(name = "tags")
    private String tags;

    @Id
    @Column(name = "msg_uniq_key")
    private String msgUniqKey;

    @Column(name = "status")
    private Integer status;

    @Column(name = "expire_time")
    private Date expireTime;

}
