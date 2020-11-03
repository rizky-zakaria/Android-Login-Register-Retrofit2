package com.example.aplikasipenyewaanbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasipenyewaanbus.api.ApiClient;
import com.example.aplikasipenyewaanbus.api.ApiInterfaces;
import com.example.aplikasipenyewaanbus.model.login.Login;
import com.example.aplikasipenyewaanbus.model.login.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    EditText etUsername, etPassword, etStatus;
    Button btnLogin;
    String Username, Password, Status;
    TextView tvRegister;
    ApiInterfaces apiInterface;
    SessionManager sessionManager;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        sessionManager = new SessionManager(LoginActivity.this);
        if(sessionManager.isLoggedIn()){
            status = sessionManager.getUserDetail().get(SessionManager.STATUS);
            if (status.equals("pegawai")){
                moveToDashboar();
            }else if (status.equals("anggota")){
                moveToMain();
            }else {
                Toast.makeText(getApplicationContext(), "Login Dulu Bossque", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                Username = etUsername.getText().toString();
                Password = etPassword.getText().toString();

                if (Username.equals("")){
//                    Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    etUsername.setError("Masukan Username");
                }else if (Password.equals("")){
//                    Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    etPassword.setError("Masukan Password");
                }else {
                    login(Username,Password, Status);
                }
//                Toast.makeText(LoginActivity.this, "nama : " + Username , Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void login(String username, String password, String status) {

        apiInterface = ApiClient.getClient().create(ApiInterfaces.class);
        Call<Login> loginCall = apiInterface.loginResponse(username, password, status);
        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){


                    // Ini untuk menyimpan sesi
                    sessionManager = new SessionManager(LoginActivity.this);
                    LoginData loginData = response.body().getLoginData();
                    sessionManager.createLoginSession(loginData);

                    //Ini untuk pindah
//                    Toast.makeText(LoginActivity.this, "Nama : " + response.body().getLoginData().getUserId(), Toast.LENGTH_SHORT).show();
                    if(response.body().getLoginData().getStatus().equals("anggota"))
                    {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
//                      Toast.makeText(getApplicationContext(), "Status", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void moveToDashboar() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    private void moveToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}