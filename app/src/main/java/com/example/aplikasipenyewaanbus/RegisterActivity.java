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

    private EditText etName,etEmail, etPassword;
    private String name, email, password;
    private Button add_user;
    ApiInterfaces apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.tv_email);
        etPassword = findViewById(R.id.tv_password);
        etName = findViewById(R.id.tv_name);

        add_user = findViewById(R.id.addUser);
        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = etName.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                 if (name.equals("")){
                    etName.setError("Masukan Username");
                }else if (email.equals("")){
                    etEmail.setError("Masukan Nama");
                }else if (password.equals("")){
                    etPassword.setError("Masukan Nama");
                }else {
                    register(name, email, password);
                }
            }
        });

    }


    private void register(String name, String email, String password){

        apiInterface = ApiClient.getClient().create(ApiInterfaces.class);
        Call<Register> call = apiInterface.postData(name, email, password);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(getApplicationContext(), "Please Wait Activation by Admin", Toast.LENGTH_SHORT).show();
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