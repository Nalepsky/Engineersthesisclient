package io.futurestud.retrofit1.api.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weapon {
    private Long id;
    private String name;
    private String range;
    private String shots;
    private String penetration;
    private List<Rule> rules;

/*    public String getRulesNames(){
        StringBuilder rulesNames = new StringBuilder();
        getRules().forEach(r -> rulesNames.append(r.getName()).append(", "));

        String result = rulesNames.toString();

        if(result.isEmpty()){
            return "-";
        }

        return  result.substring(0, result.length() - 2);
    }*/
}
