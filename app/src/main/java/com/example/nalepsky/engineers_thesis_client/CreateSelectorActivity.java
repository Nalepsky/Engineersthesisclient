package com.example.nalepsky.engineers_thesis_client;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nalepsky.engineers_thesis_client.Utils.SelectorCost;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.futurestud.retrofit1.api.model.dataHolder.EntryWithUnitNamesAndId;
import io.futurestud.retrofit1.api.model.dataHolder.SelectorDataHolder;
import io.futurestud.retrofit1.api.model.dataHolder.UnitDataHolder;
import io.futurestud.retrofit1.api.model.dataHolder.UnitNameAndId;
import io.futurestud.retrofit1.api.service.ArmyListClient;
import io.futurestud.retrofit1.api.service.EntryClient;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateSelectorActivity extends AppCompatActivity {
    private List<EntryWithUnitNamesAndId> entries;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    List<EntryWithUnitNamesAndId> expandableListTitle;
    LinkedHashMap<EntryWithUnitNamesAndId, List<UnitNameAndId>> expandableListDetail;
    private TextView selectorCostTextView;
    private Button saveButton;

    private SelectorDataHolder selectorDataHolder;
    private SelectorCost selectorCost;

    private final static int REQUEST_CODE_1 = 1;

    private Integer selectedEntryPosition = -1;
    private String selectedUnitName = "NAME";

    private Long correspondingUnitIdInDataHolder;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_selector);

        Intent i = getIntent();
        final Long selectorId = i.getLongExtra("selectorId", -1);

        selectorCost = new SelectorCost();

        selectorDataHolder = new SelectorDataHolder();
        selectorDataHolder.setId(selectorId);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        selectorCostTextView = (TextView) findViewById(R.id.current_selector_cost);
        selectorCostTextView.setText("0pts");
        saveButton = findViewById(R.id.save_button);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                selectedEntryPosition = groupPosition;

                selectedUnitName = expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).getName();

                Intent i = new Intent(getApplicationContext(), CreateUnitActivity.class);
                i.putExtra("unitId", expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).getId());

                correspondingUnitIdInDataHolder = expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).getCorrespondingUnitIdInDataHolder();

                if(correspondingUnitIdInDataHolder >= 0){
                    Gson gson = new Gson();
                    i.putExtra("unit", gson.toJson(selectorDataHolder
                            .getUnits()
                            .get(correspondingUnitIdInDataHolder.intValue())));
                }

                startActivityForResult(i, REQUEST_CODE_1);

                return false;
            }
        });

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        EntryClient client = retrofit.create(EntryClient.class);
        Call<List<EntryWithUnitNamesAndId>> call = client.getEntriesForSelectorId(selectorId);

        call.enqueue(new Callback<List<EntryWithUnitNamesAndId>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<EntryWithUnitNamesAndId>> call, Response<List<EntryWithUnitNamesAndId>> response) {
                entries = response.body();

                expandableListDetail = getSelectorStructure(entries);
                expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                expandableListAdapter = new CustomExpandableListAdapter(CreateSelectorActivity.this , expandableListTitle, expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);

            }

            @Override
            public void onFailure(Call<List<EntryWithUnitNamesAndId>> call, Throwable t) {
                Toast.makeText(CreateSelectorActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .readTimeout(1, TimeUnit.MINUTES)
                        .writeTimeout(1, TimeUnit.MINUTES)
                        .build();

                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8080")
                        .client(okHttpClient);
                //.addConverterFactory(GsonConverterFactory.create(gson));

                Retrofit retrofit = builder.build();

                ArmyListClient client = retrofit.create(ArmyListClient.class);

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(selectorDataHolder));

                Call<ResponseBody> call = client.downloadPDF(requestBody);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            boolean success = writeResponseBodyToDisk(response.body());

                            Toast.makeText(CreateSelectorActivity.this, "Army List saved", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(CreateSelectorActivity.this, "Something went wrong :<", Toast.LENGTH_SHORT).show();
                    }
                });
                /*Intent i = new Intent(getApplicationContext(), ShowArmyListActivity.class);
                i.putExtra("selector", gson.toJson(selectorDataHolder));

                startActivity(i);*/
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_1 && resultCode == RESULT_OK){
            Gson gson = new Gson();
            String strUnit = data.getStringExtra("newUnit");
            Integer newUnitCost = data.getIntExtra("cost", -1);
            UnitDataHolder newUnit = gson.fromJson(strUnit, UnitDataHolder.class);

            if(correspondingUnitIdInDataHolder == -1){
                selectorDataHolder.getUnits().add(newUnit);
                selectorCost.getUnitsCost().put(selectorDataHolder.getUnits().size() - 1, newUnitCost);
            }else{
                selectorDataHolder.getUnits().set(correspondingUnitIdInDataHolder.intValue(), newUnit);
                selectorCost.getUnitsCost().put(correspondingUnitIdInDataHolder.intValue(), newUnitCost);
            }

            selectorCostTextView.setText(selectorCost.getTotalSelectorCost().toString() + " pts");

            if(correspondingUnitIdInDataHolder == -1) {
                entries.get(selectedEntryPosition)
                        .getUnits()
                        .add(new UnitNameAndId(newUnit.getId(),
                                selectedUnitName,
                                (long) selectorDataHolder.getUnits().size() - 1));
            }

            expandableListDetail = getSelectorStructure(entries);
            expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
            expandableListAdapter = new CustomExpandableListAdapter(CreateSelectorActivity.this , expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private LinkedHashMap<EntryWithUnitNamesAndId, List<UnitNameAndId>> getSelectorStructure(List<EntryWithUnitNamesAndId> entries){
        LinkedHashMap<EntryWithUnitNamesAndId, List<UnitNameAndId>> result = new LinkedHashMap<>();

        entries.forEach(e -> {
            List<UnitNameAndId> units = new ArrayList<>(e.getUnits());

            result.put(e, units);
        });

        return result;
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) throws IOException {
        try {
            File file = new File(getExternalFilesDir(null) + File.separator + "ArmyList.pdf");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
