package com.example.nalepsky.engineers_thesis_client;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.futurestud.retrofit1.api.model.Unit;
import io.futurestud.retrofit1.api.service.UnitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateUnitActivity extends AppCompatActivity {
    Unit unit;

    TextView unitName;
    TextView costValue;
    TextView composition;
    TextView weapons;
    TextView options;
    TextView specialRules;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_unit);

        unitName = (TextView) findViewById(R.id.unitName);
        costValue = (TextView) findViewById(R.id.unitCost_value);
        composition = (TextView) findViewById(R.id.composition_value);
        weapons = (TextView) findViewById(R.id.weapons_value);
        options = (TextView) findViewById(R.id.options_value);
        specialRules = (TextView) findViewById(R.id.special_rules_value);

        Intent i = getIntent();
        final Long unitId = i.getLongExtra("unitId", -1);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        UnitClient client = retrofit.create(UnitClient.class);
        Call<Unit> call = client.getUnitById(unitId);

        call.enqueue(new Callback<Unit>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Unit> call, Response<Unit> response) {
                unit = response.body();

                System.out.println("=========" + unit.getR_cost());

                unitName.setText(unit.getName());
                costValue.setText(unit.getAllCosts());
                composition.setText(unit.getComposition());
                weapons.setText(unit.getWeaponsNamesAsString());
                options.setText(unit.getWeaponsNamesAsString());
                specialRules.setText(unit.getWeaponsNamesAsString());

                Toast.makeText(CreateUnitActivity.this, unit.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Unit> call, Throwable t) {

            }
        });
    }
}
