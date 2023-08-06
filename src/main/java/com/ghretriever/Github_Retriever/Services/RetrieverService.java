package com.ghretriever.Github_Retriever.Services;


import com.ghretriever.Github_Retriever.Entities.Application;
import com.ghretriever.Github_Retriever.Entities.Issue;
import com.ghretriever.Github_Retriever.ErrorHandling.GithubRetrievalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.ghretriever.Github_Retriever.Constants.Constants.GITHUB_ERROR;

@Slf4j
@Service
public class RetrieverService {
    @Autowired
    private RestTemplate restTemplate;

    private HttpHeaders headers;
    private HttpEntity<String> httpEntity;

    private List<Application> applications;
    @Value("${pa.token}")
    private String token;
    @Value("${user.agent}")
    private String userAgent;
    @Value("${base.url}")
    private String baseUrl;
    @Value("${issues.endpoint}")
    private String issuesEndpoint;
    @Value("${github.owner}")
    private String owner;

    public ResponseEntity<List<Issue>> getIssues(String repo) throws RestClientException
    {
        initializeRequest();
        final String constructedUrl = baseUrl + constructIssuesUrl(repo);

        ResponseEntity<List<Issue>> responseList = restTemplate.exchange(
                    constructedUrl,
                    HttpMethod.GET,
                    httpEntity,
                    new ParameterizedTypeReference<>() {
                    });

        if (!responseList.getStatusCode().is2xxSuccessful())
        {
            throw new GithubRetrievalException(GITHUB_ERROR + responseList.getBody().get(0),
                        HttpStatus.valueOf(responseList.getStatusCode().value()));
        }

        if (responseList.getBody().isEmpty())
        {
            throw new GithubRetrievalException(GITHUB_ERROR + " No issues found.",HttpStatus.NO_CONTENT);
        }

        return responseList;
    }

    public List<String> getMarkdownIssues(String repo)
    {
        return FormatterService.convertMarkdownToHtml(
                getIssues(repo).getBody().stream()
                        .map(Issue::getBody)
                        .collect(Collectors.toList())
        );
    }

    private void initializeRequest()
    {
        log.info("Initializing request. Setting Headers.");
        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.set("User-Agent", userAgent);

        log.info("Headers set: {}",headers);

        httpEntity = new HttpEntity<>(headers);
    }

    private String constructIssuesUrl(String repo)
    {
        log.info("Constructing Issues URL for {} repo",repo);
        try {
            return issuesEndpoint.replace("{owner}",owner).replace("{repo}",repo);
        }
        catch (NullPointerException e)
        {
            log.error("Nullpointer Exception occurred. Lets check for Null fields.\n" +
                    "Owner: {}\n" +
                    "Repo: {}",owner,repo);
            throw e;
        }
    }
}
