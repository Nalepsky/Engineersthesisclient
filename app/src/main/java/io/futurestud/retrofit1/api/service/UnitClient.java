package io.futurestud.retrofit1.api.service;

import java.util.List;

import io.futurestud.retrofit1.api.model.Unit;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UnitClient {

    @GET("unit/{unitId}")
    Call<Unit> getUnitById(@Path("unitId") Long unitId);
}
