package io.futurestud.retrofit1.api.model.dataHolder;

import io.futurestud.retrofit1.api.model.utils.UnitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntryWithUnitNamesAndId {
    private Long id;
    private Long min;
    private Long max;
    private UnitType type;
    private List<UnitNameAndId> units;

    @Override
    public String toString() {
        final String msg = (max.equals(1L))?" unit from:":" units from:";

        if(min.equals(max)){
            return min + " " + type + msg;
        }
        return min + " - " + max + " " + type + msg;
    }
}
