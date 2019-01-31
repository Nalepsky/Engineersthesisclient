package io.futurestud.retrofit1.api.service;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserClient {

    @POST("user/create/{email}")
    Call<Long> createUser(@Path("email") String email, @Body String password);

    @POST("user/login/{email}")
    Call<Long> loginUser(@Path("email") String email, @Body String password);
}
