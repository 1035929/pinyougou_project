package com.pinyougou.pojo;

import java.io.Serializable;

public class TbBrand implements Serializable {
    private Long id;//主键

    private String name;//品牌名称

    private String firstChar;//首字母

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar == null ? null : firstChar.trim();
    }
}