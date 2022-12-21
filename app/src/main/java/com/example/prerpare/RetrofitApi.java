package com.example.prerpare;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitApi {

    @GET("_1234Model/{Id}")
    Call<DataModal> getDATA(@Path("Id") int Id);

    @POST("_1234Model")
    Call<DataModal> createPost(@Body DataModal dataModal);

    @PUT("_1234Model/{Id}")
    Call<DataModal> updateData(@Query("Id") int Id, @Body DataModal dataModal);

    @DELETE("_1234Model/{Id}")
    Call<Void> deleteData(@Path("Id") int Id);


}
