package io.futurestud.retrofit1.api.model.dataHolder;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import io.futurestud.retrofit1.api.model.utils.ExperienceLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDataHolder {
    private Long id;
    private ExperienceLevel experienceLevel;
    private Integer numberOfAdditionalModels = 0;
    private Set<OptionsDataHolder> options = new HashSet<>();

    @Override
    public String toString() {
        return "UnitDataHolder{" +
                "id=" + id +
                ", experienceLevel=" + experienceLevel +
                ", numberOfAdditionalModels=" + numberOfAdditionalModels +
                ", optionsSize=" + options.size() +
                '}';
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Integer getCountOfOptionById(Long id){
        final Integer[] result = new Integer[1];
        result[0] = 0;

        options.forEach(o -> {
            if(o.getId().equals(id)){
                result[0] = o.getCount();
            }
        });

        return result[0];
    }
}