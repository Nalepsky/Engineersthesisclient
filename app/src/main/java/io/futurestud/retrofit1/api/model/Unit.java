package io.futurestud.retrofit1.api.model;

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
    private Long id;
    private String name;
    private Nation nation;
    private UnitType type;
    private String composition;
    private Integer baseNumber;
    private Integer maxNumber;
    private Integer iCost;
    private Integer rCost;
    private Integer vCost;
    private Integer IAdditionalCost;
    private Integer RAdditionalCost;
    private Integer VAdditionalCost;
    private List<Rule> rules;
    private List<Option> options;
    private List<Weapon> weapons;

    @Override
    public String toString() {
        return getName();
    }

    public Integer getAdditionalCost(ExperienceLevel level){
        Map<ExperienceLevel, Integer> cost = new HashMap<>();
        cost.put(ExperienceLevel.INEXPERIENCED, getIAdditionalCost());
        cost.put(ExperienceLevel.REGULAR, getRAdditionalCost());
        cost.put(ExperienceLevel.VETERAN, getVAdditionalCost());

        return cost.get(level);
    }

    public Integer getCost(ExperienceLevel level){
        Map<ExperienceLevel, Integer> cost = new HashMap<>();
        cost.put(ExperienceLevel.INEXPERIENCED, getICost());
        cost.put(ExperienceLevel.REGULAR, getRCost());
        cost.put(ExperienceLevel.VETERAN, getVCost());

        return cost.get(level);
    }
}
