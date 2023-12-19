package com.mudxx.demo.boot.jpa.modules.table.service.dto;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/11/20 15:36
 */
public class SysDictCodeSaveDto implements Serializable {
    private static final long serialVersionUID = 172327528145521050L;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 名称（英文）
     */
    private String nameEn;
    /**
     * 上级ID
     */
    private String pid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

}
