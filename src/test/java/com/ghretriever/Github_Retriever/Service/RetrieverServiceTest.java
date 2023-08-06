package com.ghretriever.Github_Retriever.Service;

import com.ghretriever.Github_Retriever.Entities.Issue;
import com.ghretriever.Github_Retriever.ErrorHandling.GithubRetrievalException;
import com.ghretriever.Github_Retriever.Services.RetrieverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class RetrieverServiceTest {

    @InjectMocks
    private RetrieverService service;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity http = new HttpEntity<>(headers);

        ReflectionTestUtils.setField(service,"baseUrl","http://localhost:8080");
        ReflectionTestUtils.setField(service,"issuesEndpoint", "issues");
        ReflectionTestUtils.setField(service,"owner","test user");
        ReflectionTestUtils.setField(service,"token", "example_token");
        ReflectionTestUtils.setField(service,"userAgent","tester");
        ReflectionTestUtils.setField(service,"headers", headers);
        ReflectionTestUtils.setField(service,"httpEntity",http);
    }

    @Test
    public void getIssues_Success()
    {
        //Arrange
        List<Issue> issues = new ArrayList<>();
        issues.add(new Issue());
        issues.get(0).setBody("Test issue");

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenReturn(new ResponseEntity<>(issues,HttpStatus.OK));

        ResponseEntity<List<Issue>> response = service.getIssues("TEST");
        assertNotNull(response);
        assertEquals(response.getBody(),issues);
    }

    @Test
    public void getIssues_NoneFound()
    {
        //Arrange
        List<Issue> issues = new ArrayList<>();

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenReturn(new ResponseEntity<>(issues,HttpStatus.NO_CONTENT));

        assertThrows(GithubRetrievalException.class, () ->
        {
            service.getIssues("anything");
        });
    }

    @Test
    public void getIssues_BadRequest()
    {
        //Arrange
        List<Issue> issues = new ArrayList<>();
        issues.add(new Issue());
        issues.get(0).setBody("Test issue");

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenReturn(new ResponseEntity<>(issues,HttpStatus.BAD_REQUEST));

        assertThrows(GithubRetrievalException.class, () ->
        {
            service.getIssues("anything");
        });
    }

    @Test
    public void getMarkdown()
    {
        //Arrange
        List<Issue> issues = new ArrayList<>();
        issues.add(new Issue());
        issues.get(0).setBody("## Test issue");

        List<String> expected = new ArrayList<>();
        expected.add("<h2>Test issue</h2>\n");

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenReturn(new ResponseEntity<>(issues,HttpStatus.OK));

        List<String> actual = service.getMarkdownIssues("ANY");

        assertEquals(expected.size(),actual.size());
        assertEquals(expected,actual);

    }
}
