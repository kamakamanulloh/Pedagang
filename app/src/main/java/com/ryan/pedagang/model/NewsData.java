package com.ryan.pedagang.model;

/**
 * Created by chukamak on 26/11/2017.
 */



public class NewsData {
    private String id, alamat, datetime, keterangan, gambar,nama,jenis;

    public NewsData() {
    }

    public NewsData(String id, String alamat, String datetime, String keterangan, String gambar, String nama,String jenis) {
        this.id = id;
        this.alamat = alamat;
        this.datetime = datetime;
        this.gambar = gambar;
        this.nama = nama;
        this.keterangan = keterangan;
        this.jenis=jenis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getketerangan() {
        return keterangan;
    }

    public void setketerangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }


    public String getJenis() {
        return jenis;
    }

    public void setJenis(String status) {
        this.jenis = jenis;
    }

}
