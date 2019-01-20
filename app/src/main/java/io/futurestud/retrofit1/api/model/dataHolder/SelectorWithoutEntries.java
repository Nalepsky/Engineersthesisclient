package io.futurestud.retrofit1.api.model.dataHolder;

import io.futurestud.retrofit1.api.model.Nation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SelectorWithoutEntries {
    private Long id;
    private String name;
    private Nation nation;

    @Override
    public String toString() {
        return nation.getName() + " | " + name;
    }
}
