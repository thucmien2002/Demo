package com.poly.assignment.Retrofits;

import com.poly.assignment.Category;
import com.poly.assignment.Product;
import com.poly.assignment.Requests.NewProductRequest;
import com.poly.assignment.Requests.RegisterRequest;
import com.poly.assignment.Requests.UpdateProductRequest;
import com.poly.assignment.Requests.UploadRequest;
import com.poly.assignment.Responses.NewProductResponse;
import com.poly.assignment.Responses.RegisterResponse;
import com.poly.assignment.Responses.UpdateProductResponse;
import com.poly.assignment.Responses.UploadResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface iRetrofitService {

    @POST("/views/user-register.php")
    Call<RegisterResponse> register(@Body RegisterRequest body);

    @POST("/views/user-login.php")
    Call<RegisterResponse> login(@Body RegisterRequest body);

    @GET("/views/get-all-product.php")
    Call<List<Product>> getAllProduct();

    @GET("/views/get-all-sp.php")
    Call<List<Product>> getAllSP();

    @GET("/views/get-categories.php")
    Call<List<Category>> getCategories();

    @GET("/views/get-one-category.php")
    Call<List<Category>> getOneCategory(@Query("id") Integer id);

    @POST("/views/upload.php")
    Call<UploadResponse> upload(@Body UploadRequest body);

    @POST("/views/insert-product.php")
    Call<NewProductResponse> insertProduct(@Body NewProductRequest body);

    @POST("/views/insert-sp.php")
    Call<NewProductResponse> insertSP(@Body NewProductRequest body);

    @POST("/views/update-product.php")
    Call<UpdateProductResponse> updateProduct(@Body UpdateProductRequest body);

    @GET("/views/delete-product.php")
    Call<RegisterResponse> deleteProduct(@Query("id") Integer id);

    @GET("/views/forgot-password.php")
    Call<RegisterResponse> forgotPassword(@Query("email") String email);
}
