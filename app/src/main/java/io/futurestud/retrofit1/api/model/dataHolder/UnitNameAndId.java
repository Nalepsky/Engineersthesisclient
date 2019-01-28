package io.futurestud.retrofit1.api.model.dataHolder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnitNameAndId {
    private Long id;
    private String name;
    private Long correspondingUnitIdInDataHolder;

    public UnitNameAndId(Long id, String name) {
        this.id = id;
        this.name = name;
        this.correspondingUnitIdInDataHolder = -1L;
    }

    public UnitNameAndId() {
        this.correspondingUnitIdInDataHolder = -1L;
    }

    @Override
    public String toString() {
        return name + " " + correspondingUnitIdInDataHolder;
    }
}
