package com.example.aplikasipenyewaanbus.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginData {


    @SerializedName("user_id")
    private String userId;

    @SerializedName("nama")
    private String nama;

    @SerializedName("username")
    private String username;

    @SerializedName("ni")
    private String ni;

    @SerializedName("alamat")
    private String alamat;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("status")
    private String status;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNi() {
        return ni;
    }

    public void setNi(String ni) {
        this.ni = ni;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    @SerializedName("password")
    private String password;

    @SerializedName("telepon")
    private String telepon;


    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public void setName(String nama){
        this.nama = nama;
    }

    public String getName(){
        return nama;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

}
