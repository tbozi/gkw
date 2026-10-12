package com.se.demorestdb.model;

public class TinTuc {
    private int id;
    private String tieuDe;
    private String noiDung;
    private String lienKet;
    private Integer danhmucId;

    public TinTuc() {
    }

    public TinTuc(Integer danhmucId, int id, String lienKet, String noiDung, String tieuDe) {
        this.danhmucId = danhmucId;
        this.id = id;
        this.lienKet = lienKet;
        this.noiDung = noiDung;
        this.tieuDe = tieuDe;
    }

    public Integer getDanhmucId() {
        return danhmucId;
    }

    public void setDanhmucId(Integer danhmucId) {
        this.danhmucId = danhmucId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLienKet() {
        return lienKet;
    }

    public void setLienKet(String lienKet) {
        this.lienKet = lienKet;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }
}
