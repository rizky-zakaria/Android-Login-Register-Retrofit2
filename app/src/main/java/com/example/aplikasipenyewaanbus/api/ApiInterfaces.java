package com.example.aplikasipenyewaanbus.api;

import com.example.aplikasipenyewaanbus.model.login.Login;
import com.example.aplikasipenyewaanbus.model.register.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterfaces {

    @FormUrlEncoded
    @POST("login.php")
    Call<Login> loginResponse(
            @Field("username") String username,
            @Field("password") String password,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<Register> postData(
            @Field("username") String username,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("status") String status,
            @Field("telepon") String telepon,
            @Field("password") String password
    );

}
