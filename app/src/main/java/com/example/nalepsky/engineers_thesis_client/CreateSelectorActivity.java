package com.example.nalepsky.engineers_thesis_client;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.futurestud.retrofit1.api.model.Entry;
import io.futurestud.retrofit1.api.service.EntryClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateSelectorActivity extends ListActivity {
    private ArrayAdapter<Entry> adapter;
    private List<Entry> entries;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_armylist);

        Intent i = getIntent();
        final Long selectorId = i.getLongExtra("selectorId", -1);

        entries = new ArrayList<>();
        ListView entriesListView = (ListView) findViewById(android.R.id.list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, entries);
        entriesListView.setAdapter(adapter);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        EntryClient client = retrofit.create(EntryClient.class);
        Call<List<Entry>> call = client.getEntriesForSelectorId(selectorId);

        call.enqueue(new Callback<List<Entry>>() {
            @Override
            public void onResponse(Call<List<Entry>> call, Response<List<Entry>> response) {
                entries = response.body();
                adapter.addAll(entries);
            }

            @Override
            public void onFailure(Call<List<Entry>> call, Throwable t) {
                Toast.makeText(CreateSelectorActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
