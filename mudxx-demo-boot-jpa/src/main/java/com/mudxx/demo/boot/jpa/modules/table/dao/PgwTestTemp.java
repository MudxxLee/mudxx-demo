package com.mudxx.demo.boot.jpa.modules.table.dao;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/9/21 09:14
 */
@Data
@Entity
@Table(name = "pgw_test_temp")
public class PgwTestTemp implements Serializable {
    @Id
    @GeneratedValue(generator = "sys_uuid")
    @GenericGenerator(name = "sys_uuid", strategy = "uuid")
    @Column(length = 125, nullable = false)
    private String id;

    @Column(name = "aa")
    private Boolean aa;

    @Column(name = "bb")
    private Integer bb;
}