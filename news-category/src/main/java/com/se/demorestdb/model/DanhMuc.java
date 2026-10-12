package com.se.demorestdb.model;

public class DanhMuc {
    private int id;
    private String tenDanhMuc;
    private String nguoiQuanLi;
    private String ghiChu;

    public DanhMuc() {
    }

    public DanhMuc(String ghiChu, int id, String nguoiQuanLi, String tenDanhMuc) {
        this.ghiChu = ghiChu;
        this.id = id;
        this.nguoiQuanLi = nguoiQuanLi;
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNguoiQuanLi() {
        return nguoiQuanLi;
    }

    public void setNguoiQuanLi(String nguoiQuanLi) {
        this.nguoiQuanLi = nguoiQuanLi;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }
}
