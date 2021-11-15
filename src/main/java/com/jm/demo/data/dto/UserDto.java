package com.jm.demo.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @JsonProperty(required = true)
    @NotBlank
    private String name;

    @JsonProperty(required = true)
    @NotBlank
    @Email
    private String email;

    @JsonProperty(required = true)
    @NotBlank
    private String username;

    @JsonProperty(required = true)
    @NotBlank
    private String password;
}
