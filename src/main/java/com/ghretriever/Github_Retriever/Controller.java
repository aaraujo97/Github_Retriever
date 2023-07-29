package com.ghretriever.Github_Retriever;

import com.ghretriever.Github_Retriever.Entities.Issue;
import com.ghretriever.Github_Retriever.Services.RetrieverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private RetrieverService retriever;

    @GetMapping("/getIssues")
    public String getIssues()
    {
        List<Issue> issues = retriever.getIssues("LIFTR");

        return issues.toString();
    }
}
