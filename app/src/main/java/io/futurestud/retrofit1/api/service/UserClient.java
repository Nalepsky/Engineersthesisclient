package io.futurestud.retrofit1.api.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserClient {

    @POST("user/create/{email}")
    Call<Long> createUser(@Path("email") String email, @Body String password);

    @POST("user/login/{email}")
    Call<Long> loginUser(@Path("email") String email, @Body String password);

    @POST("user/savearmylist/{id}")
    Call<Boolean> saveArmyList(@Path("{id") Long id, @Body String jsonArmyList);

    @GET("user/getarmylists/{id}")
    Call<List<String>> getArmyLists(@Path("{id") Long userId);
}
