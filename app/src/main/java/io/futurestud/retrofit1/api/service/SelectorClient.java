package io.futurestud.retrofit1.api.service;

import java.util.List;

import io.futurestud.retrofit1.api.model.dataHolder.SelectorWithoutEntries;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SelectorClient {

    @GET("/selector/withoutEntries")
    Call<List<SelectorWithoutEntries>> getSelectors();
}
