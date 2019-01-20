package io.futurestud.retrofit1.api.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Selector {
    private Long id;
    private String name;
    private Nation nation;
    private List<Entry> entries;
}
