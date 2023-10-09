package com.ghretriever.Github_Retriever;

import com.ghretriever.Github_Retriever.DTO.NewIssueDTO;
import com.ghretriever.Github_Retriever.Entities.Issue;
import com.ghretriever.Github_Retriever.Services.FormatterService;
import com.ghretriever.Github_Retriever.Services.RetrieverService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private RetrieverService retriever;

    @Autowired
    private FormatterService formatterService;


    @CrossOrigin(origins = "*")
    @GetMapping("/issues/{repo}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved issues."),
                    @ApiResponse(responseCode = "400", description = "You sent a bad request."),
                    @ApiResponse(responseCode = "404", description = "Repository did not exist."),
                    @ApiResponse(responseCode = "204", description = "Repo exists. But no issues were found."),
                    @ApiResponse(responseCode = "500", description = "Oops. Something unexpected happened.")
            }
    )
    public List<Issue> getIssues(@PathVariable String repo)
    {
        ResponseEntity<List<Issue>> response = retriever.getIssues(repo);

        return response.getBody();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/issues/{repo}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully created issue."),
                    @ApiResponse(responseCode = "400", description = "You sent a bad request."),
                    @ApiResponse(responseCode = "404", description = "Repository did not exist."),
                    @ApiResponse(responseCode = "500", description = "Oops. Something unexpected happened.")
            }
    )
    public ResponseEntity<Void> createIssue(@RequestBody NewIssueDTO newIssue)
    {
        if ( retriever.createNewIssue(newIssue).getStatusCode() == HttpStatusCode.valueOf(201))
        {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/markdown/{repo}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully got the markdown."),
                    @ApiResponse(responseCode = "400", description = "You sent a bad request."),
                    @ApiResponse(responseCode = "404", description = "Repository did not exist."),
                    @ApiResponse(responseCode = "204", description = "Repo exists. But no issues were found.")
            }
            )
    public List<String> getMarkdown(@PathVariable String repo)
    {
        return retriever.getMarkdownIssues(repo);
    }
}
