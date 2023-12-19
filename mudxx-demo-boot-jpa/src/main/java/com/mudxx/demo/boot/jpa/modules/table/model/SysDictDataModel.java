package com.mudxx.demo.boot.jpa.modules.table.model;

import java.io.Serializable;

/**
 * @author laiw
 * @date 2023/11/22 14:48
 */
public class SysDictDataModel implements Serializable {
    private static final long serialVersionUID = -2762962601398585223L;
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
     * 值
     */
    private String value;

    public SysDictDataModel() {
    }

    private SysDictDataModel(Builder builder) {
        setCode(builder.code);
        setName(builder.name);
        setNameEn(builder.nameEn);
        setValue(builder.value);
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final class Builder {
        private String code;
        private String name;
        private String nameEn;
        private String value;

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

        public Builder value(String val) {
            value = val;
            return this;
        }

        public SysDictDataModel build() {
            return new SysDictDataModel(this);
        }
    }
}
