package com.example.nalepsky.engineers_thesis_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class CreateUnitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_unit);

        Intent i = getIntent();
        final Long unitId = i.getLongExtra("unitId", -1);

        Toast.makeText(CreateUnitActivity.this, unitId.toString(), Toast.LENGTH_SHORT).show();
    }
}
