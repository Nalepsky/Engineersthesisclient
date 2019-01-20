package com.example.nalepsky.engineers_thesis_client;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.futurestud.retrofit1.api.model.Selector;
import io.futurestud.retrofit1.api.model.dataHolder.SelectorWithoutEntries;
import io.futurestud.retrofit1.api.service.SelectorClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChooseSelectorActivity extends ListActivity {
    private ArrayAdapter<SelectorWithoutEntries> adapter;
    private List<SelectorWithoutEntries> selectors;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_selector);

        selectors = new ArrayList<>();
        ListView selectorsListView = (ListView) findViewById(android.R.id.list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectors);
        selectorsListView.setAdapter(adapter);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        SelectorClient client = retrofit.create(SelectorClient.class);
        Call<List<SelectorWithoutEntries>> call = client.getSelectors();

        call.enqueue(new Callback<List<SelectorWithoutEntries>>() {
            @Override
            public void onResponse(Call<List<SelectorWithoutEntries>> call, Response<List<SelectorWithoutEntries>> response) {
                selectors = response.body();
                adapter.addAll(selectors);
            }

            @Override
            public void onFailure(Call<List<SelectorWithoutEntries>> call, Throwable t) {
                Toast.makeText(ChooseSelectorActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        selectorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), CreateSelectorActivity.class);
                i.putExtra("selectorId", selectors.get(position).getId());
                startActivity(i);
            }
        });
    }
}
