package io.futurestud.retrofit1.api.service;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ArmyListClient {
    //@POST("create/getpdf")
    //Call<byte[]> createPDF(@Body RequestBody json);

    @POST("create/getpdf")
    Call<ResponseBody> downloadPDF(@Body RequestBody json);
}
