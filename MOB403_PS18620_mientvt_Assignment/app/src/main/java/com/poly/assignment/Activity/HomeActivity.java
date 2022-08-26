package com.poly.assignment.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.poly.assignment.Product;
import com.poly.assignment.ProductAdapter;
import com.poly.assignment.R;
import com.poly.assignment.Responses.RegisterResponse;
import com.poly.assignment.Retrofits.RetrofitBuilder;
import com.poly.assignment.Retrofits.iRetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    List<Product> list;
    ListView listViewProducts;
    ProductAdapter adapter;
    Button btnAdd;

    private iRetrofitService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnAdd = findViewById(R.id.btnAdd);
        list = new ArrayList<>();
        listViewProducts = findViewById(R.id.listViewProducts3);
        adapter = new ProductAdapter(this, list);
        listViewProducts.setAdapter(adapter);
        service = RetrofitBuilder.createService(iRetrofitService.class);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, AddActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }

    public void loadProducts() {
        service.getAllSP().enqueue(getAllSP);
    }

    Callback<List<Product>> getAllSP = new Callback<List<Product>>() {
        @Override
        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
            if (response.isSuccessful()) {
                List<Product> registerResponse = response.body();
                Log.d(">>> getAllProduct", "onResponse: " + registerResponse.toString());
                adapter.updateData(registerResponse);
            }
        }

        @Override
        public void onFailure(Call<List<Product>> call, Throwable t) {
            Log.d(">>> getAllProduct", "onFailure: " + t.getMessage());
        }
    };

}