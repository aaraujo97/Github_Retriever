package com.ghretriever.Github_Retriever.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewIssueDTO {
    @NotNull
    private String belongsTo;
    @NotNull
    private String title;
    private String body;

    //TODO: Possibly integrate other fields in the future
}
