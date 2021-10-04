package com.softwarejm.demojava17.controller;

import com.softwarejm.demojava17.model.Role;
import com.softwarejm.demojava17.model.User;
import com.softwarejm.demojava17.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

import static com.softwarejm.demojava17.config.Paths.*;

@RestController
@RequestMapping(USERS_URI)
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Collection<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @PostMapping
    public ResponseEntity<User> newUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(API_PATH + USERS_PATH).toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PatchMapping("/{username}" + ROLES_PATH + "/{roleName}")
    public ResponseEntity<Role> addRoleToUser(@PathVariable String username, @PathVariable String roleName) {
        userService.addRoleToUser(username, roleName);
        return ResponseEntity.noContent().build();
    }
}
