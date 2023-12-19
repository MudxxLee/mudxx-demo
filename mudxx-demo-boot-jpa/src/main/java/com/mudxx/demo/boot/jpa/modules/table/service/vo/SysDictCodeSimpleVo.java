package com.mudxx.demo.boot.jpa.modules.table.service.vo;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/11/20 10:50
 */
public class SysDictCodeSimpleVo implements Serializable {
    private static final long serialVersionUID = -831213034020392900L;
    /**
     * ID
     */
    private String id;
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
     * 等级 等级（0：根、1：一级、2：二级、3：三级依次类推）
     */
    private Integer grade;
    /**
     * 上级ID
     */
    private String pid;
    /**
     * 启用状态 启用状态 （0：禁用；1：启用）
     */
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
