package com.poly.assignment.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.poly.assignment.Product;
import com.poly.assignment.R;
import com.poly.assignment.Requests.RegisterRequest;
import com.poly.assignment.Responses.RegisterResponse;
import com.poly.assignment.Retrofits.RetrofitBuilder;
import com.poly.assignment.Retrofits.iRetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText edtEmailForgotPassword;
    Button btnAccept;
    TextView tvLogin;

    private iRetrofitService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        edtEmailForgotPassword = findViewById(R.id.edtEmailForgotPassword);
        btnAccept = findViewById(R.id.btnAccept);
        tvLogin = findViewById(R.id.tvLogin2);

        service = RetrofitBuilder.createService(iRetrofitService.class);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onAccept(View view) {
        String email = edtEmailForgotPassword.getText().toString();
        RegisterRequest request = new RegisterRequest(email);
        service.forgotPassword(email).enqueue(forgotPassword);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    Callback<RegisterResponse> forgotPassword = new Callback<RegisterResponse>() {
        @Override
        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
            if (response.isSuccessful()) {
                Log.d(">>> forgotPassword", "onResponse: " + response.body().getStatus().toString());
            }
        }

        @Override
        public void onFailure(Call<RegisterResponse> call, Throwable t) {
            Log.d(">>> forgotPassword", "onFailure: " + t.getMessage());
        }
    };
}