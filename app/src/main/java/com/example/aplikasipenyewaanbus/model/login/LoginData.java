package com.example.aplikasipenyewaanbus.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginData {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("id")
    private String id;
    @SerializedName("is_active")
    private String is_active;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("image")
    private String image;
    @SerializedName("password")
    private String password;
    @SerializedName("role_id")
    private String role_id;
    @SerializedName("date_created")
    private String date_created;

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
}
