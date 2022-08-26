package com.poly.assignment.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.assignment.R;
import com.poly.assignment.Requests.RegisterRequest;
import com.poly.assignment.Responses.RegisterResponse;
import com.poly.assignment.Retrofits.RetrofitBuilder;
import com.poly.assignment.Retrofits.iRetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmailLogin, edtPasswordLogin;
    Button btnLogin;
    TextView tvRegister, tvForgotPassword;
    private iRetrofitService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
//        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        service = RetrofitBuilder.createService(iRetrofitService.class);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
//        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onForgotPassword(view);
//            }
//        });
    }

    public void onLogin(View view) {
        String email = edtEmailLogin.getText().toString();
        String password = edtPasswordLogin.getText().toString();
        RegisterRequest request = new RegisterRequest(email, password);
        service.login(request).enqueue(login);
    }

    Callback<RegisterResponse> login = new Callback<RegisterResponse>() {
        @Override
        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
            if (response.isSuccessful()) {
                RegisterResponse registerResponse = response.body();
                if (registerResponse.getResult()) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onFailure(Call<RegisterResponse> call, Throwable t) {
            Log.d(">>> login", "onFailure: " + t.getMessage());
        }
    };

//    public void onForgotPassword(View view) {
//        //bấm nút chuyển sang màn hình Forgot, nhập email, gọi API
////        Log.d(">>>>>>", "Quên mật khẩu");
//        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
//        startActivity(intent);
//    }
}