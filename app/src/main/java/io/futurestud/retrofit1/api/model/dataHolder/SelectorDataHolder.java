package io.futurestud.retrofit1.api.model.dataHolder;

import android.os.Build;
import android.support.annotation.RequiresApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectorDataHolder {
    private Long id;
    private Integer points;
    private List<UnitDataHolder> units = new ArrayList<>();

    public Integer getUnitsSize(){
        return units.size();
    }

    @Override
    public String toString() {
        return "Saved Selector " + getPoints();
    }
}
