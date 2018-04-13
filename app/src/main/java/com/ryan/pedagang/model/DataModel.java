package com.ryan.pedagang.model;

/**
 * Created by chukamak on 20/03/2018.
 */

public class DataModel {
    private String id, nama,jenis,tgl,kec;

    public DataModel() {
    }

    public DataModel(String id, String nama) {
        this.id = id;
        this.nama = nama;
        this.jenis = jenis;
        this.tgl=tgl;
        this.kec=kec;
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

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
    public String getTgl(){
        return tgl;
    }
    public void setTgl(String tgl){
        this.tgl=tgl;
    }

    public String getKec(){
        return kec;
    }
    public void setkec(String kec){
        this.kec=kec;
    }
}

