package com.ghretriever.Github_Retriever.Entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {

    public String url;
    public long id;
    public int number;
    public String title;
    public String state;
    public LocalDateTime created_at;
    public LocalDateTime updated_at;
    public LocalDateTime closed_at;
    public String body;


}
