package com.example.aplikasipenyewaanbus.api;

import com.example.aplikasipenyewaanbus.model.login.Login;
import com.example.aplikasipenyewaanbus.model.register.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterfaces {

    @FormUrlEncoded
    @POST("Login")
    Call<Login> loginResponse(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("Auth")
    Call<Register> postData(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

}
