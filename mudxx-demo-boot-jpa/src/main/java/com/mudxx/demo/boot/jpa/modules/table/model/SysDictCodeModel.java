package com.mudxx.demo.boot.jpa.modules.table.model;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/11/20 10:50
 */
public class SysDictCodeModel implements Serializable {
    private static final long serialVersionUID = -831213034020392900L;
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

    public SysDictCodeModel() {

    }

    private SysDictCodeModel(Builder builder) {
        setCode(builder.code);
        setName(builder.name);
        setNameEn(builder.nameEn);
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

    public static final class Builder {
        private String code;
        private String name;
        private String nameEn;

        public Builder() {
        }

        public Builder code(String val) {
            code = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder nameEn(String val) {
            nameEn = val;
            return this;
        }

        public SysDictCodeModel build() {
            return new SysDictCodeModel(this);
        }
    }
}
