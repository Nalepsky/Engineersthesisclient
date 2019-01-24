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
    private Integer inexpCost;
    private Integer regCost;
    private Integer vetCost;
    private Integer inexpAdditionalCost;
    private Integer regAdditionalCost;
    private Integer vetAdditionalCost;
    private List<Rule> rules;
    private List<Option> options;
    private List<Weapon> weapons;

    @Override
    public String toString() {
        return getName();
    }

    public Integer getAdditionalCost(ExperienceLevel level){
        Map<ExperienceLevel, Integer> cost = new HashMap<>();
        cost.put(ExperienceLevel.INEXPERIENCED, getInexpAdditionalCost());
        cost.put(ExperienceLevel.REGULAR, getRegAdditionalCost());
        cost.put(ExperienceLevel.VETERAN, getVetAdditionalCost());

        return cost.get(level);
    }

    public Integer getCost(ExperienceLevel level){
        Map<ExperienceLevel, Integer> cost = new HashMap<>();
        cost.put(ExperienceLevel.INEXPERIENCED, getInexpCost());
        cost.put(ExperienceLevel.REGULAR, getRegCost());
        cost.put(ExperienceLevel.VETERAN, getVetCost());

        return cost.get(level);
    }

    public String getAllCosts() {
        StringBuilder result = new StringBuilder();

        if(getInexpCost() > -1){
            result.append(" Inexperienced: ").append(getInexpCost()).append("pts ");
        }

        if(getRegCost() > -1){
            result.append(" Regular: ").append(getRegCost()).append("pts ");
        }

        if(getVetCost() > -1){
            result.append(" Veteran: ").append(getVetCost()).append("pts ");
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
