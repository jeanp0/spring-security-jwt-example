package com.jm.demo.controller;

import com.jm.demo.config.constants.Paths;
import com.jm.demo.data.dto.ErrorResponse;
import com.jm.demo.data.dto.UserDto;
import com.jm.demo.data.model.Role;
import com.jm.demo.data.model.User;
import com.jm.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = Paths.USERS_PATH, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {

    private static final String USER_CREATED_LOG = "User was created:{}";
    private static final String USER_UPDATED_LOG = "User was updated:{}";

    private final UserService userService;

    @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "Get all users", description = "Get all users description")
    @ApiResponse(responseCode = "200", description = "Users successfully obtained")
    @GetMapping
    public ResponseEntity<Collection<User>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @Operation(summary = "Get a user by id", description = "Get a user by id description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully obtained"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping(path = "/{userId}")
    public ResponseEntity<User> findById(@PathVariable Integer userId) {
        return ResponseEntity.ok().body(userService.findById(userId));
    }

    @Operation(summary = "Create a user", description = "Create a user description")
    @ApiResponse(responseCode = "201", description = "User successfully created")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@Valid @RequestBody UserDto user) {
        final User userCreated = userService.create(user);
        final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(Paths.USERS_PATH + "/" + userCreated.getUserId()).toUriString());
        log.info(USER_CREATED_LOG, userCreated);
        return ResponseEntity.created(uri).body(userCreated);
    }

    @Operation(summary = "Update a user", description = "Update a user description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully updated"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PutMapping(path = "/{userId}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> update(@PathVariable Integer userId, @Valid @RequestBody UserDto user) {
        final User userUpdated = userService.update(userId, user);
        log.info(USER_UPDATED_LOG, userUpdated.toString());
        return ResponseEntity.ok(userUpdated);
    }

    @Operation(summary = "Add role to user", description = "Add role to user description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Role successfully added to user"),
            @ApiResponse(responseCode = "404", description = "User or role not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PatchMapping(path = "/{userId}" + Paths.ROLES_PATH + "/{roleName}")
    public ResponseEntity<Role> addRoleToUser(@PathVariable Integer userId, @PathVariable String roleName) {
        userService.addRoleByUserId(userId, roleName);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a user", description = "Delete a user description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Integer userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
