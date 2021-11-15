package com.jm.demo.controller;

import com.jm.demo.data.model.Role;
import com.jm.demo.data.dto.RoleDto;
import com.jm.demo.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.jm.demo.config.constants.Paths.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(ROLES_PATH)
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Create a role", description = "Create a role description")
    @ApiResponse(responseCode = "201", description = "Role successfully created")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Role> create(@RequestBody RoleDto role) {
        final Role roleCreated = roleService.create(role);
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ROLES_PATH + "/" + roleCreated.getRoleId()).toUriString());
        return ResponseEntity.created(uri).body(roleCreated);
    }
}
