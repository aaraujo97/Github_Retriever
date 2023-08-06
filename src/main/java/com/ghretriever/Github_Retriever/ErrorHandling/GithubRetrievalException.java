package com.ghretriever.Github_Retriever.ErrorHandling;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class GithubRetrievalException extends RuntimeException{
    private final HttpStatus status;

    public GithubRetrievalException(String message, HttpStatus status)
    {
        super(message);
        this.status = status;
    }

}
