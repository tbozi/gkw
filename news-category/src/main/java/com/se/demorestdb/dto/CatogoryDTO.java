package com.se.demorestdb.dto;

import com.se.demorestdb.model.TinTuc;

public class CatogoryDTO {
    private TinTuc tinTuc;
    private String categoryName;

    public CatogoryDTO() {
    }

    public CatogoryDTO(String categoryName, TinTuc tinTuc) {
        this.categoryName = categoryName;
        this.tinTuc = tinTuc;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public TinTuc getTinTuc() {
        return tinTuc;
    }

    public void setTinTuc(TinTuc tinTuc) {
        this.tinTuc = tinTuc;
    }
}
