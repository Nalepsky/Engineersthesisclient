package io.futurestud.retrofit1.api.model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.futurestud.retrofit1.api.model.utils.ExperienceLevel;
import io.futurestud.retrofit1.api.model.utils.UnitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Unit {

    //TODO make camelCase names, and json converter (JsonProperty?)
    private Long id;
    private String name;
    private Nation nation;
    private UnitType type;
    private String composition;
    private Integer baseNumber;
    private Integer maxNumber;
    private Integer i_cost;
    private Integer r_cost;
    private Integer v_cost;
    private Integer additional_i_cost;
    private Integer additional_r_cost;
    private Integer additional_v_cost;
    private List<Rule> rules;
    private List<Option> options;
    private List<Weapon> weapons;

    @Override
    public String toString() {
        return getName();
    }

    public Integer getAdditionalCost(ExperienceLevel level){
        Map<ExperienceLevel, Integer> cost = new HashMap<>();
        cost.put(ExperienceLevel.INEXPERIENCED, getAdditional_i_cost());
        cost.put(ExperienceLevel.REGULAR, getAdditional_r_cost());
        cost.put(ExperienceLevel.VETERAN, getAdditional_v_cost());

        return cost.get(level);
    }

    public Integer getCost(ExperienceLevel level){
        Map<ExperienceLevel, Integer> cost = new HashMap<>();
        cost.put(ExperienceLevel.INEXPERIENCED, getI_cost());
        cost.put(ExperienceLevel.REGULAR, getR_cost());
        cost.put(ExperienceLevel.VETERAN, getV_cost());

        return cost.get(level);
    }

    public String getAllCosts() {
        StringBuilder result = new StringBuilder();

        if(getI_cost() != null){
            result.append(" Inexperienced: ").append(getI_cost()).append("pts ");
        }

        if(getR_cost() != null){
            result.append(" Regular: ").append(getR_cost()).append("pts ");
        }

        if(getV_cost() != null){
            result.append(" Veteran: ").append(getV_cost()).append("pts ");
        }

        return result.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getWeaponsNamesAsString(){
        StringBuilder result = new StringBuilder();

        getWeapons().forEach(w -> result.append(w.getName() + " "));

        return result.toString();
    }
}
