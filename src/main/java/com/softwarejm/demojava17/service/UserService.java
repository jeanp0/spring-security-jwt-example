package com.softwarejm.demojava17.service;

import com.softwarejm.demojava17.model.Role;
import com.softwarejm.demojava17.model.User;

import java.util.Collection;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    Collection<User> getUsers();
}
