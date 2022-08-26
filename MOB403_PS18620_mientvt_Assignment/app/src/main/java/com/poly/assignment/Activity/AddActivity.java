package com.poly.assignment.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.poly.assignment.Category;
import com.poly.assignment.CategoryAdapter;
import com.poly.assignment.Product;
import com.poly.assignment.R;
import com.poly.assignment.Requests.NewProductRequest;
import com.poly.assignment.Requests.UploadRequest;
import com.poly.assignment.Responses.NewProductResponse;
import com.poly.assignment.Responses.UploadResponse;
import com.poly.assignment.Retrofits.RetrofitBuilder;
import com.poly.assignment.Retrofits.iRetrofitService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {

    List<Category> list;
    CategoryAdapter adapter;
    Button btnBack, btnSave;
    EditText edtProductName, edtProductPrice, edtProductQuantity;
    TextView tvTakePicture;
    ImageView imgProduct;
    Spinner spCategories;

    private iRetrofitService service;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        list = new ArrayList<>();
//        spCategories = findViewById(R.id.spCategories);

        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSaveAdd);
        edtProductName = findViewById(R.id.edtProductName);
        edtProductPrice = findViewById(R.id.edtProductPrice);
//        edtProductQuantity = findViewById(R.id.edtProductQuantity);
//        tvTakePicture = findViewById(R.id.tvTakePicture);
//        imgProduct = findViewById(R.id.imgProduct);
//        runtimePermission();

        service = RetrofitBuilder.createService(iRetrofitService.class);
//        loadCategory();
//        if (!checkCamera()) {
//            requestCamera();
//        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


//        spCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                spCategories.setSelection(0);
//            }
//        });
    }

    public void onAddClick(View view) {
        String name = edtProductName.getText().toString();
//        Integer quantity = Integer.parseInt(edtProductQuantity.getText().toString());
        Float price = Float.parseFloat(edtProductPrice.getText().toString());
//        Integer index = spCategories.getSelectedItemPosition();
//        Integer category_id = list.get(index).getId();
        NewProductRequest request = new NewProductRequest(name, price);
        service.insertSP(request).enqueue(insertSP);
    }

//    public void loadCategory() {
//        service.getCategories().enqueue(getCategories);
//    }

//    public Boolean checkCamera() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_DENIED) {
//            return false;
//        }
//        return true;
//    }
//
//    public void requestCamera() {
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.CAMERA}, 2000);
//    }

//    public void onTakePictureClick() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        try {
//            activityResultLauncher.launch(intent);
//        } catch (Exception e) {
//            Log.d(">>>>>>>>>", "onTakePictureClick" + e.getMessage());
//        }
//    }
//
//    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    Bundle bundle = result.getData().getExtras();
//                    Bitmap bitmap = (Bitmap) bundle.get("data");
//                    imgProduct.setImageBitmap(bitmap);
//                    // upload hình dạng base64 lên server
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
//                    byte[] bytes = baos.toByteArray();
//
//                    String base64 = Base64.encodeToString(bytes, 0);
//                    UploadRequest request = new UploadRequest(base64);
//                    service.upload(request).enqueue(uploadImage);
//                }
//            });
//
//    Callback<UploadResponse> uploadImage = new Callback<UploadResponse>() {
//        @Override
//        public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
//            if (response.isSuccessful()) {
//                UploadResponse result = response.body();
//                Log.d(">>>>>>uploadResponse", result.getPath());
//                path = result.getPath();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<UploadResponse> call, Throwable t) {
//            Log.d(">>> uploadImage", "onFailure: " + t.getMessage());
//        }
//    };
//
//    Callback<List<Category>> getCategories = new Callback<List<Category>>() {
//        @Override
//        public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
//            if (response.isSuccessful()) {
//                List<Category> result = response.body();
//                list.addAll(result);
//                adapter = new CategoryAdapter(AddActivity.this, list);
//                spCategories.setAdapter(adapter);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<List<Category>> call, Throwable t) {
//            Log.d(">>> getCategories", "onFailure: " + t.getMessage());
//        }
//    };

    Callback<NewProductResponse> insertSP = new Callback<NewProductResponse>() {
        @Override
        public void onResponse(Call<NewProductResponse> call, Response<NewProductResponse> response) {
            if (response.isSuccessful()) {
                NewProductResponse result = response.body();
                Toast.makeText(AddActivity.this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void onFailure(Call<NewProductResponse> call, Throwable t) {
            Toast.makeText(AddActivity.this, "Thêm sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
            Log.d(">>>", "insert: " + t.getMessage());
        }
    };

//    private void runtimePermission() {
//        Dexter.withContext(AddActivity.this).withPermission(Manifest.permission.CAMERA)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                        tvTakePicture.setOnClickListener(v->onTakePictureClick());
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//
//                    }
//                }).check();
//    }


}