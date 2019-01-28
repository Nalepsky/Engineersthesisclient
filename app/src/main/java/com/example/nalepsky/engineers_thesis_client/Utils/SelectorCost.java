package com.example.nalepsky.engineers_thesis_client.Utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectorCost {
    //specific unit id and its total cost
    private Map<Integer, Integer> unitsCost = new HashMap<>();


    @TargetApi(Build.VERSION_CODES.N)
    public Integer getTotalSelectorCost(){
        final Integer [] cost = new Integer[1];
        cost[0] = 0;

        unitsCost.forEach((i, c) -> cost[0] += c);

        return cost[0];
    }
}
