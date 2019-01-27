package com.example.nalepsky.engineers_thesis_client.Utils;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnitCost {
    private Integer baseCost = 0;
    private Integer additionalModelsCost = 0;
    private Integer additionalModelsNumber = 0;
    private Map<Long, Integer> optionsCost = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Integer getTotalCost(){
        return baseCost + additionalModelsCost * additionalModelsNumber + getTotalOptionsCost();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Integer getTotalOptionsCost(){
        final Integer[] sum = new Integer[1];
        sum[0] = 0;

        optionsCost.forEach((id, cost) -> {
            sum[0] = sum[0] + cost;
        });

        return sum[0];
    }
}
