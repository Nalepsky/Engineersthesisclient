package com.example.nalepsky.engineers_thesis_client;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nalepsky.engineers_thesis_client.Utils.UnitCost;
import com.google.gson.Gson;

import java.util.List;

import io.futurestud.retrofit1.api.model.Rule;
import io.futurestud.retrofit1.api.model.Unit;
import io.futurestud.retrofit1.api.model.Weapon;
import io.futurestud.retrofit1.api.model.dataHolder.OptionsDataHolder;
import io.futurestud.retrofit1.api.model.dataHolder.UnitDataHolder;
import io.futurestud.retrofit1.api.model.utils.ExperienceLevel;
import io.futurestud.retrofit1.api.service.UnitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateUnitActivity extends AppCompatActivity {
    Unit unit;
    UnitCost unitCost;

    TextView currentUnitPointsValue;
    Button back;
    Button save;

    TextView unitName;
    ViewGroup costValue;
    TextView composition;
    TextView weapons;
    LinearLayout additionalModels;
    LinearLayout options;
    TextView specialRules;

    private UnitDataHolder unitDataHolder;
    private Integer additionalUnitsCostDescriptionId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_unit);

        unitCost = new UnitCost();

        currentUnitPointsValue = (TextView) findViewById(R.id.current_unit_cost);
        back = findViewById(R.id.back_button);
        save = findViewById(R.id.save_button);

        unitName = (TextView) findViewById(R.id.unitName);
        costValue = (ViewGroup ) findViewById(R.id.unit_base_cost_value);
        composition = (TextView) findViewById(R.id.composition_value);
        weapons = (TextView) findViewById(R.id.weapons_value);
        additionalModels = (LinearLayout) findViewById(R.id.additional_models);
        options = (LinearLayout) findViewById(R.id.options_value);
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
            @SuppressLint("ResourceType")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Unit> call, Response<Unit> response) {
                unit = response.body();
                unitDataHolder = new UnitDataHolder();
                unitDataHolder.setId(unit.getId());

                unitName.setText(unit.getName());
                createCostValues(unit);
                composition.setText(unit.getComposition());
                weapons.setText(createWeaponsList(unit.getWeapons()));
                createAdditionalModelsValues(unit);
                createOptionsList(unit);
                specialRules.setText(createRulesList(unit.getRules()));
            }

            @Override
            public void onFailure(Call<Unit> call, Throwable t) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Gson gson = new Gson();
                intent.putExtra("newUnit", gson.toJson(unitDataHolder));
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void createAdditionalModelsValues(Unit unit){
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        TextView description = new TextView(this);

        if(!unit.getMaxNumber().equals(unit.getBaseNumber())){
            Integer additionalModelsNumber = unit.getMaxNumber() - unit.getBaseNumber();
            additionalUnitsCostDescriptionId = 9999;
            description.setId(additionalUnitsCostDescriptionId);

            NumberPicker numberPicker = new NumberPicker(CreateUnitActivity.this);
            numberPicker.setMaxValue(additionalModelsNumber);
            numberPicker.setMinValue(0);

            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    unitDataHolder.setNumberOfAdditionalModels(numberPicker.getValue());

                    unitCost.setAdditionalModelsNumber( numberPicker.getValue());
                    currentUnitPointsValue.setText(unitCost.getTotalCost().toString());
                }
            });

            layout.addView(numberPicker);
            layout.addView(description);

            additionalModels.addView(layout);

            setAdditionalModelsDescription(unit);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createOptionsList(Unit unit){
        unit.getOptions().forEach(o -> {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);

            TextView description = new TextView(this);
            description.setText(o.getDescription());

            NumberPicker numberPicker = new NumberPicker(CreateUnitActivity.this);
            numberPicker.setMaxValue(o.getMaxNumber());
            numberPicker.setMinValue(0);
            numberPicker.setId(o.getId().intValue());

            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    if(numberPicker.getValue() == 0){
                        Long id = (long) numberPicker.getId();
                        Integer value = numberPicker.getValue();
                        unitDataHolder.getOptions().remove(new OptionsDataHolder(id, value));

                        unitCost.getOptionsCost().remove(id);

                        currentUnitPointsValue.setText(unitCost.getTotalCost().toString());
                    }else{
                        Long id = (long) numberPicker.getId();
                        Integer value = numberPicker.getValue();
                        unitDataHolder.getOptions().add(new OptionsDataHolder(id, value));

                        unitCost.getOptionsCost().put(id, value * unit.getOptionById(id).getCost());
                        currentUnitPointsValue.setText(unitCost.getTotalCost().toString());
                    }
                }
            });

            layout.addView(numberPicker);
            layout.addView(description);

            options.addView(layout);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ResourceType")
    private void createCostValues(Unit unit) {
        if(unit.getInexpCost() >= 0){
            RadioButton inexpButton = new RadioButton(this);
            inexpButton.setId(1);
            inexpButton.setText(String.format("inexperienced: %d pts", unit.getInexpCost()));
            inexpButton.setChecked(true);
            unitDataHolder.setExperienceLevel(ExperienceLevel.INEXPERIENCED);
            costValue.addView(inexpButton);

            unitCost.setBaseCost(unit.getInexpCost());
            unitCost.setAdditionalModelsCost(unit.getInexpAdditionalCost());
            currentUnitPointsValue.setText(unitCost.getTotalCost().toString());

            inexpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inexpButton.setChecked(true);
                    unitDataHolder.setExperienceLevel(ExperienceLevel.INEXPERIENCED);

                    setAdditionalModelsDescription(unit);

                    unitCost.setBaseCost(unit.getInexpCost());
                    unitCost.setAdditionalModelsCost(unit.getInexpAdditionalCost());
                    currentUnitPointsValue.setText(unitCost.getTotalCost().toString());
                }
            });
        }
        if(unit.getRegCost() >= 0){
            RadioButton regButton = new RadioButton(this);
            regButton.setId(2);
            regButton.setText(String.format("regular: %d pts", unit.getRegCost()));
            regButton.setChecked(true);
            unitDataHolder.setExperienceLevel(ExperienceLevel.REGULAR);
            costValue.addView(regButton);

            unitCost.setBaseCost(unit.getRegCost());
            unitCost.setAdditionalModelsCost(unit.getRegAdditionalCost());
            currentUnitPointsValue.setText(unitCost.getTotalCost().toString());

            regButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    regButton.setChecked(true);
                    unitDataHolder.setExperienceLevel(ExperienceLevel.REGULAR);

                    setAdditionalModelsDescription(unit);

                    unitCost.setBaseCost(unit.getRegCost());
                    unitCost.setAdditionalModelsCost(unit.getRegAdditionalCost());
                    currentUnitPointsValue.setText(unitCost.getTotalCost().toString());
                }
            });
        }
        if(unit.getVetCost() >= 0){
            RadioButton vetButton = new RadioButton(this);
            vetButton.setId(3);
            vetButton.setText(String.format("veteran: %d pts", unit.getVetCost()));
            vetButton.setChecked(true);
            unitDataHolder.setExperienceLevel(ExperienceLevel.VETERAN);
            costValue.addView(vetButton);

            unitCost.setBaseCost(unit.getVetCost());
            unitCost.setAdditionalModelsCost(unit.getVetAdditionalCost());
            currentUnitPointsValue.setText(unitCost.getTotalCost().toString());

            vetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vetButton.setChecked(true);
                    unitDataHolder.setExperienceLevel(ExperienceLevel.VETERAN);

                    setAdditionalModelsDescription(unit);

                    unitCost.setBaseCost(unit.getVetCost());
                    unitCost.setAdditionalModelsCost(unit.getVetAdditionalCost());
                    currentUnitPointsValue.setText(unitCost.getTotalCost().toString());
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String createRulesList(List<Rule> rules){
        if(rules.size() == 0){
            return "none";
        }

        StringBuilder builder = new StringBuilder();

        rules.forEach(r -> builder.append(r.getName() + "\n"));

        return builder.toString().substring(0, builder.length() - 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String createWeaponsList(List<Weapon> weapons){
        if(weapons.size() == 0){
            return "none";
        }

        StringBuilder builder = new StringBuilder();

        weapons.forEach(w -> builder.append(w.getName() + "\n"));

        return builder.toString().substring(0, builder.length() - 1);
    }

    @SuppressLint("SetTextI18n")
    private void setAdditionalModelsDescription(Unit unit){
        TextView description = findViewById(additionalUnitsCostDescriptionId);

        Integer additionalModelsNumber = unit.getMaxNumber() - unit.getBaseNumber();
        Integer cost = unit.getAdditionalCost(unitDataHolder.getExperienceLevel());
        description.setText("add up to " + additionalModelsNumber + " additional soldiers for +" + cost + " pts each");
    }
}
