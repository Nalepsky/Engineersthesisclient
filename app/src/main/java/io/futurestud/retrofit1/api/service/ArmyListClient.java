package io.futurestud.retrofit1.api.service;

import java.util.List;

import io.futurestud.retrofit1.api.model.dataHolder.SelectorDataHolder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ArmyListClient {

    @POST("create/getpdf/{userId}")
    Call<ResponseBody> downloadPDF(@Path("userId") Long userId, @Body RequestBody json);

    @GET("create/getarmylists/{userId}")
    Call<List<String>> getArmyLists(@Path("userId") Long userId);
}
