package com.softwarejm.demojava17.controller;

import com.softwarejm.demojava17.model.Role;
import com.softwarejm.demojava17.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.softwarejm.demojava17.config.Paths.*;

@RestController
@RequestMapping(ROLES_URI)
@RequiredArgsConstructor
public class RoleController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Role> newRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(API_PATH + ROLES_PATH).toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }
}
