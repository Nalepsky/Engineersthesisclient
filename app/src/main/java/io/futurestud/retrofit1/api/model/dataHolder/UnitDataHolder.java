package io.futurestud.retrofit1.api.model.dataHolder;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

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
    private Integer numberOfAdditionalModels;
    private Set<OptionsDataHolder> options = new HashSet<>();
}