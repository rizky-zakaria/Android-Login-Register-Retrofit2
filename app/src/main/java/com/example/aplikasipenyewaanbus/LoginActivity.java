package com.example.aplikasipenyewaanbus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
            status = sessionManager.getUserDetail().get(SessionManager.ROLE_ID);
            if (status.equals("1")){
                moveToDashboar();
            }else if (status.equals("2")){
                moveToMain();
            }else {
                showNotif();
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
                    login(Username,Password);
                }
//                Toast.makeText(LoginActivity.this, "nama : " + Username , Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void login(String email, String password) {

        apiInterface = ApiClient.getClient().create(ApiInterfaces.class);
        Call<Login> loginCall = apiInterface.loginResponse(email, password);
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
                    if(response.body().getLoginData().getIs_active().equals("1"))
                    {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        showNotif();
//                      Toast.makeText(getApplicationContext(), "Anda Belum Di Aktifasi, Tunggu Sesaat Lagi", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
                    }

                } else {
//                    Toast.makeText(LoginActivity.this, "Perikasi Kembali Username Dan Password Anda", Toast.LENGTH_SHORT).show();
                    showNotif();
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

    //notif
    private void showNotif() {
        String NOTIFICATION_CHANNEL_ID = "channel_androidnotif";
        Context context = this.getApplicationContext();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channelName = "Android Notif Channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        Intent mIntent = new Intent(this, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("fromnotif", "notif");
        mIntent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher_background))
                .setTicker("notif starting")
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLights(Color.RED, 3000, 3000)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("Maaf Akun Anda Belum Diaktifkan Oleh Admin")
                .setContentText("www.zakaria.id");

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(115, builder.build());
    }
}