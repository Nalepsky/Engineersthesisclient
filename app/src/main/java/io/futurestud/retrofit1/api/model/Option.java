package io.futurestud.retrofit1.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    private Long id;
    private Weapon weapon;
    private Rule rule;
    private String description;
    private Integer cost;
    private Integer maxNumber;

    public String getWeaponOrRule(){
        if(weapon != null){
            return weapon.getName();
        }
        return rule.getName();
    }

    public Object getOptionClass(){
        return (weapon != null)? Rule.class: Weapon.class;
    }
}
