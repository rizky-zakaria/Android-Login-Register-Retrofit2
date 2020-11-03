package com.example.aplikasipenyewaanbus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasipenyewaanbus.api.ApiClient;
import com.example.aplikasipenyewaanbus.api.ApiInterfaces;
import com.example.aplikasipenyewaanbus.model.register.Register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNi, etUsername, etNama, etAlamat, etStatus, etPassword, etTelepon;
    private String ni, username, nama, alamat, status, password, telepon;
    private Button add_user;
    ApiInterfaces apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etAlamat = findViewById(R.id.tv_alamat);
        etNama = findViewById(R.id.tv_nama);
        etPassword = findViewById(R.id.tv_password);
        etStatus = findViewById(R.id.tv_status);
        etUsername = findViewById(R.id.tvusername);
        etTelepon = findViewById(R.id.tv_telepon);


        add_user = findViewById(R.id.addUser);
        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nama = etNama.getText().toString();
                username = etUsername.getText().toString();
                alamat = etAlamat.getText().toString();
                telepon = etTelepon.getText().toString();
                status = etStatus.getText().toString();
                password = etPassword.getText().toString();

                 if (username.equals("")){
                    etUsername.setError("Masukan Username");
                }else if (nama.equals("")){
                    etNama.setError("Masukan Nama");
                }else if (alamat.equals("")){
                    etAlamat.setError("Masukan Username");
                }else if (status.equals("")){
                    etStatus.setError("Masukan Nama");
                }else if (telepon.equals("")){
                    etTelepon.setError("Masukan Username");
                }else if (password.equals("")){
                    etPassword.setError("Masukan Nama");
                }else {
                    register(ni, username, nama, alamat, status, telepon, password);
                }
            }
        });

    }


    private void register(String ni, String username, String nama, String alamat, String status, String telepon, String password){

        apiInterface = ApiClient.getClient().create(ApiInterfaces.class);
        Call<Register> call = apiInterface.postData(username, nama, alamat, status, telepon, password);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(getApplicationContext(), "Berhasil Mendaftarkan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}