package com.nursery.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "status")
    private boolean status;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "response")
    private Object response;

    public ApiResponse(boolean status, String message, Object response) {
        this.status = status;
        this.message = message;
        this.response = response;
    }


}
