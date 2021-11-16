package com.jm.demo.service;

import com.jm.demo.auth.UserDetailsImpl;
import com.jm.demo.data.dto.UserDto;
import com.jm.demo.data.model.Role;
import com.jm.demo.data.model.User;
import com.jm.demo.config.exception.NotFoundException;
import com.jm.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    private final static String FIND_ALL_LOG = "Fetching all users";
    private final static String FIND_LOG = "Fetching user {}";
    private final static String CREATE_LOG = "Creating user {}";
    private final static String UPDATE_LOG = "Updating user {}";
    private final static String DELETE_LOG = "Deleting user {}";
    private final static String ADD_ROLE_LOG = "Adding role {} to user {}";
    private final static String NOT_FOUND_BY_ID = "User with id %s not found";
    private final static String NOT_FOUND_BY_USERNAME = "User with username %s not found";

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = findByUsername(username);
        Collection<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(user, authorities);
    }

    public Collection<User> findAll() {
        log.info(FIND_ALL_LOG);
        return userRepository.findAll();
    }

    public User findById(Integer userId) {
        log.info(FIND_LOG, userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, userId)));
    }

    public User findByUsername(String username) {
        log.info(FIND_LOG, username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_USERNAME, username)));
    }

    public User create(UserDto user) {
        log.info(CREATE_LOG, user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        final User userMapped = modelMapper.map(user, User.class);
        return userRepository.save(userMapped);
    }

    public User update(Integer userId, UserDto user) {
        log.info(UPDATE_LOG, user.getName());
        User userEntity = findById(userId);
        String password = passwordEncoder.encode(user.getPassword());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(password);
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        return userEntity;
    }

    public void addRoleByUserId(Integer userId, String roleName) {
        log.info(ADD_ROLE_LOG, roleName, userId);
        User user = findById(userId);
        Role role = roleService.findByName(roleName);
        user.getRoles().add(role);
    }

    public void addRoleByUsername(String username, String roleName) {
        log.info(ADD_ROLE_LOG, roleName, username);
        User user = findByUsername(username);
        Role role = roleService.findByName(roleName);
        user.getRoles().add(role);
    }

    public void delete(Integer userId) {
        log.info(DELETE_LOG, userId);
        userRepository.deleteById(userId);
    }
}
