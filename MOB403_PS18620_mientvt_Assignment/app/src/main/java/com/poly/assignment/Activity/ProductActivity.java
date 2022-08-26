package com.poly.assignment.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.poly.assignment.Product;
import com.poly.assignment.ProductAdapter;
import com.poly.assignment.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ProductActivity extends AppCompatActivity {

    ListView listViewProducts;
    ProductAdapter adapter;
    List<Product> list = new ArrayList<>();

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                List<Product> _list = new ArrayList<>();
                JSONArray array = new JSONArray(msg.obj.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject product = array.getJSONObject(i);
                    Integer id = product.getInt("id");
                    String name = product.getString("name");
                    Float price = Float.parseFloat(product.getString("price"));
                    Integer quantity = product.getInt("quantity");
                    String image = product.getString("image");
                    Integer category_id = product.getInt("category_id");
                    Product p = new Product(id, name, price, quantity, image, category_id);
                    _list.add(p);
                    Log.d(">>>>>", "handleMessage: " + name);
                }
                list.clear();
                list.addAll(_list);
                adapter = new ProductAdapter(ProductActivity.this, list);
                listViewProducts.setAdapter(adapter);
            } catch (Exception e) {
                Log.d("==>", e.getMessage());
            }
        }
    };

    ThreadPoolExecutor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        listViewProducts = findViewById(R.id.listViewProducts);
        adapter = new ProductAdapter(this, list);
        listViewProducts.setAdapter(adapter);
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        loadProducts();
    }

    public void loadProducts() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.0.2.2:8085/views/get-all-product.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.connect();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(url.openStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    reader.close();

                    Message message = new Message();
                    message.obj = stringBuilder.toString();
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Log.d(">>>>", e.getMessage());
                }
            }
        });
    }

}