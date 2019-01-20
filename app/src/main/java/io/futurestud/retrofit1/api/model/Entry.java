package io.futurestud.retrofit1.api.model;

import java.util.List;

import io.futurestud.retrofit1.api.model.utils.UnitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entry {
    private Long id;
    private Long min;
    private Long max;
    private UnitType type;
    private List<Unit> units;

    @Override
    public String toString() {
        if(min.equals(max)){
            return min + " " + type + " units from:";
        }
        return min + " - " + max + " " + type + " units from:";
    }
}
