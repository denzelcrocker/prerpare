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

    @GET("GamesModels/{Id}")
    Call<DataModal> getDATA(@Path("Id") int Id);

    @POST("GamesModels")
    Call<DataModal> createPost(@Body DataModal dataModal);

    @PUT("GamesModels/{Id}")
    Call<DataModal> updateData(@Query("Id") int Id, @Body DataModal dataModal);

    @DELETE("GamesModels/{Id}")
    Call<Void> deleteData(@Path("Id") int Id);


}
