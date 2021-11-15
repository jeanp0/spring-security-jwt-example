package com.jm.demo.data.dto;

import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {

    private Instant timestamp;

    private int status;

    private List<String> errors;

    private String type;

    private String path;

    private String message;
}
