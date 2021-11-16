package com.jm.demo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private Instant timestamp;

    private int status;

    private List<String> errors;

    private String type;

    private String path;

    private String message;
}
