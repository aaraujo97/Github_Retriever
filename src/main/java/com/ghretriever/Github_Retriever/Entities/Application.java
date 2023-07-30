package com.ghretriever.Github_Retriever.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Application {

    private String eai;
    private String name;
    private String technology;
    private String platform;

    public Application()
    {
        this.eai = "-";
        this.name = "-";
        this.technology = "-";
        this.platform = "-";
    }

}
