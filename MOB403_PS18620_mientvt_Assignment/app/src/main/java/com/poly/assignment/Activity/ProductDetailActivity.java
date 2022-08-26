package com.poly.assignment.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.poly.assignment.Category;
import com.poly.assignment.CategoryAdapter;
import com.poly.assignment.MyVolley;
import com.poly.assignment.Product;
import com.poly.assignment.ProductAdapter;
import com.poly.assignment.R;
import com.poly.assignment.Requests.NewProductRequest;
import com.poly.assignment.Requests.RegisterRequest;
import com.poly.assignment.Requests.UpdateProductRequest;
import com.poly.assignment.Requests.UploadRequest;
import com.poly.assignment.Responses.NewProductResponse;
import com.poly.assignment.Responses.RegisterResponse;
import com.poly.assignment.Responses.UpdateProductResponse;
import com.poly.assignment.Responses.UploadResponse;
import com.poly.assignment.Retrofits.RetrofitBuilder;
import com.poly.assignment.Retrofits.iRetrofitService;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    Button btnBack, btnUpdate;
    private Integer id;
    private String name;
    private Float price;
    private Integer quantity;
    private String image;
    private Integer category_id;
    EditText edtProductDetailName, edtProductDetailPrice, edtProductDetailQuantity;
    ImageView imgProductDetailImage;
    Spinner spinner;
    private iRetrofitService service;
    List<Category> list;
    CategoryAdapter adapter;
    private String category;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        list = new ArrayList<>();
        btnBack = findViewById(R.id.btnBack);
        edtProductDetailName = findViewById(R.id.edtProductDetailName);
        edtProductDetailPrice = findViewById(R.id.edtProductDetailPrice);
        edtProductDetailQuantity = findViewById(R.id.edtProductDetailQuantity);
        imgProductDetailImage = findViewById(R.id.ivProductDetailImage);
        spinner = findViewById(R.id.spinner);
        btnUpdate = findViewById(R.id.btnUpdate);
        service = RetrofitBuilder.createService(iRetrofitService.class);

        Intent intent = this.getIntent();
        id = intent.getIntExtra("id", -1);
        name = intent.getStringExtra("name");
        image = intent.getStringExtra("image");
        price = intent.getFloatExtra("price", -1);
        quantity = intent.getIntExtra("quantity", -1);
        category_id = intent.getIntExtra("category_id", -1);
        Glide.with(this).load(image).into(imgProductDetailImage);

        edtProductDetailName.setText(name);
        edtProductDetailPrice.setText(price.toString());
        edtProductDetailQuantity.setText(quantity.toString());

        Glide.with(this).load(image).into(imgProductDetailImage);
        loadoneCategory();
        loadCategory();
        runtimePermission();
        if (!checkCamera()) {
            requestCamera();
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadCategory() {
        service.getCategories().enqueue(getCategories);
    }

    public void loadoneCategory() {
        service.getOneCategory(category_id).enqueue(getOneCategories);
    }

    public void updateProduct(View view) {
        Integer update_id = id;
        String update_name = edtProductDetailName.getText().toString();
        Float update_price = Float.parseFloat(edtProductDetailPrice.getText().toString());
        Integer update_quantity = Integer.parseInt(edtProductDetailQuantity.getText().toString());
        Integer index = spinner.getSelectedItemPosition();
        Integer update_category_id = list.get(index).getId();
        path = image;
        UpdateProductRequest request = new UpdateProductRequest(update_id, update_name, update_price, update_quantity, path, update_category_id);
        service.updateProduct(request).enqueue(updateProduct);
    }

    public void onDeleteClick() {
        service.deleteProduct(id).enqueue(deleteProduct);
    }

    public void deleteProduct(View view) {
        onDialogDelete(Gravity.CENTER);
    }

    private void onDialogDelete(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_delete);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        }

        Button btnDialogDelete = dialog.findViewById(R.id.btn_dialog_delete);
        Button btnDialogCancel = dialog.findViewById(R.id.btn_dialog_cancel);

        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnDialogDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteClick();
            }
        });

        dialog.show();
    }

    Callback<List<Category>> getCategories = new Callback<List<Category>>() {
        @Override
        public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
            if (response.isSuccessful()) {
                List<Category> result = response.body();
                list.addAll(result);
                adapter = new CategoryAdapter(ProductDetailActivity.this, list);
                spinner.setAdapter(adapter);
                int index = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getName().equalsIgnoreCase(category)) {
                        index = i;
                    }
                }
                spinner.setSelection(index);
                Log.d(">>> getCategories", "onResponse: ");
            }
        }

        @Override
        public void onFailure(Call<List<Category>> call, Throwable t) {
            Log.d(">>> getCategories", "onFailure: " + t.getMessage());
        }
    };
    Callback<List<Category>> getOneCategories = new Callback<List<Category>>() {
        @Override
        public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
            if (response.isSuccessful()) {
                List<Category> result = response.body();
                category = result.get(0).getName();
            }
        }

        @Override
        public void onFailure(Call<List<Category>> call, Throwable t) {
            Log.d(">>> getCategories", "onFailure: " + t.getMessage());
        }
    };

    Callback<UpdateProductResponse> updateProduct = new Callback<UpdateProductResponse>() {
        @Override
        public void onResponse(Call<UpdateProductResponse> call, Response<UpdateProductResponse> response) {
            if (response.isSuccessful()) {
                UpdateProductResponse result = response.body();
                Toast.makeText(ProductDetailActivity.this, "Cập nhật sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void onFailure(Call<UpdateProductResponse> call, Throwable t) {
            Toast.makeText(ProductDetailActivity.this, "Cập nhật phẩm thất bại!", Toast.LENGTH_SHORT).show();
        }
    };

    Callback<RegisterResponse> deleteProduct = new Callback<RegisterResponse>() {
        @Override
        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
            if (response.isSuccessful()) {
                RegisterResponse result = response.body();
                Log.d(">>>", "result" + result.getResult());
                Toast.makeText(ProductDetailActivity.this, "Xóa sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void onFailure(Call<RegisterResponse> call, Throwable t) {
            Toast.makeText(ProductDetailActivity.this, "Xóa sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
            Log.d(">>>>>", "onFailure: " + t.getMessage());
        }
    };

    public Boolean checkCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_DENIED) {
            return false;
        }
        return true;
    }

    public void requestCamera() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}, 2000);
    }

    public void onTakePictureUpdateClick() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            activityResultLauncher.launch(intent);
        } catch (Exception e) {
            Log.d(">>>>>>>>>", "onTakePictureUpdateClick" + e.getMessage());
        }
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    imgProductDetailImage.setImageBitmap(bitmap);
                    // upload hình dạng base64 lên server
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
                    byte[] bytes = baos.toByteArray();

                    String base64 = Base64.encodeToString(bytes, 0);
                    UploadRequest request = new UploadRequest(base64);
                    service.upload(request).enqueue(uploadImage);
                }
            });

    Callback<UploadResponse> uploadImage = new Callback<UploadResponse>() {
        @Override
        public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
            if (response.isSuccessful()) {
                UploadResponse result = response.body();
                Log.d(">>>>>>uploadResponse", result.getPath());
                image = result.getPath();
            }
        }

        @Override
        public void onFailure(Call<UploadResponse> call, Throwable t) {
            Log.d(">>> uploadImage", "onFailure: " + t.getMessage());
        }
    };

    private void runtimePermission() {
        Dexter.withContext(ProductDetailActivity.this).withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        imgProductDetailImage.setOnClickListener(v -> onTakePictureUpdateClick());
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();
    }

}