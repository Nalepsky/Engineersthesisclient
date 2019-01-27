package io.futurestud.retrofit1.api.model.dataHolder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectorDataHolder {
    private Long id;
    private Integer points;
    private List<UnitDataHolder> units;

    public Integer getUnitsSize(){
        return units.size();
    }
}
