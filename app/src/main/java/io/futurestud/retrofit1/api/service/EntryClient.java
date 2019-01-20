package io.futurestud.retrofit1.api.service;

import java.util.List;

import io.futurestud.retrofit1.api.model.Entry;
import io.futurestud.retrofit1.api.model.dataHolder.SelectorWithoutEntries;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EntryClient {

    @GET("/entry/selector/{selectorId}")
    Call<List<Entry>> getEntriesForSelectorId(@Path("selectorId")Long selectorId);
}
