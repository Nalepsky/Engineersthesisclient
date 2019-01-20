package io.futurestud.retrofit1.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rule {
    private Long id;
    private String name;
    private String description;
    private String source;
    private Integer page;
}
