package io.futurestud.retrofit1.api.model.dataHolder;

import io.futurestud.retrofit1.api.model.utils.ExperienceLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UnitDataHolder {
    private Long id;
    private ExperienceLevel experienceLevel;
    private Integer numberOfAdditionalModels;
    private Set<OptionsDataHolder> options;
}
