package com.poly.assignment.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.poly.assignment.R;
import com.poly.assignment.Requests.RegisterRequest;
import com.poly.assignment.Responses.RegisterResponse;
import com.poly.assignment.Retrofits.RetrofitBuilder;
import com.poly.assignment.Retrofits.iRetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextView tvLogin;
    EditText edtEmailRegister, edtPasswordRegister;
    Button btnRegister;

    private iRetrofitService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tvLogin = findViewById(R.id.tvLogin);
        edtEmailRegister = findViewById(R.id.edtEmailRegister);
        edtPasswordRegister = findViewById(R.id.edtPasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);
        service = RetrofitBuilder.createService(iRetrofitService.class);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onRegister(View view) {
        String email = edtEmailRegister.getText().toString();
        String password = edtPasswordRegister.getText().toString();
        RegisterRequest request = new RegisterRequest(email, password);
        service.register(request).enqueue(register);
    }

    Callback<RegisterResponse> register = new Callback<RegisterResponse>() {
        @Override
        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
            if (response.isSuccessful()) {
                RegisterResponse registerResponse = response.body();
                finish();
            }
        }

        @Override
        public void onFailure(Call<RegisterResponse> call, Throwable t) {
            Log.d(">>> register", "onFailure: " + t.getMessage());
        }
    };
}