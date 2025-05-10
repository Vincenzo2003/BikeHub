package com.vincenzo.bikehub.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.HashMap;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private String apiPath;

    private String message;

    private HashMap<String, Object> details;

    private Instant timestamp;

}
