package io.futurestud.retrofit1.api.model.dataHolder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UnitNameAndId {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return name + "";
    }
}
