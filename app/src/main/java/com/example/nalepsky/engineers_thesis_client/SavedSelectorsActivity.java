package com.example.nalepsky.engineers_thesis_client;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nalepsky.engineers_thesis_client.Utils.SingletonUser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.futurestud.retrofit1.api.model.dataHolder.SelectorDataHolder;
import io.futurestud.retrofit1.api.model.dataHolder.SelectorWithoutEntries;
import io.futurestud.retrofit1.api.service.ArmyListClient;
import io.futurestud.retrofit1.api.service.SelectorClient;
import io.futurestud.retrofit1.api.service.ServerProperties;
import io.futurestud.retrofit1.api.service.UserClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SavedSelectorsActivity extends AppCompatActivity {
    private ArrayAdapter<SelectorDataHolder> adapter;
    private List<SelectorDataHolder> selectors;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_selectors);

        selectors = new ArrayList<>();
        ListView selectorsListView = (ListView) findViewById(android.R.id.list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectors);
        selectorsListView.setAdapter(adapter);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ServerProperties.IP)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ArmyListClient client = retrofit.create(ArmyListClient.class);
        Call<List<String>> call = client.getArmyLists(SingletonUser.getUserId());

        call.enqueue(new Callback<List<String>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                Gson gson = new Gson();
                response.body()
                        .forEach(s -> selectors.add(gson.fromJson(s, SelectorDataHolder.class)));

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(SavedSelectorsActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        selectorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), CreateSelectorActivity.class);
                i.putExtra("selectorId", selectors.get(position).getId());
                Gson gson = new Gson();
                i.putExtra("selectorDataHolder", gson.toJson(selectors.get(position)));
                startActivity(i);
            }
        });
    }
}
