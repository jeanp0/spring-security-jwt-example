package com.jm.demo.config;

import com.jm.demo.data.dto.RoleDto;
import com.jm.demo.data.dto.UserDto;
import com.jm.demo.service.RoleService;
import com.jm.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.jm.demo.constants.Names.*;
import static com.jm.demo.constants.Names.ROLE_USER;

@Configuration
public class CommonConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner run(UserService userService, RoleService roleService) {
        return args -> {
            if (roleService.findAll().isEmpty()) {
                roleService.create(new RoleDto(ROLE_USER));
                roleService.create(new RoleDto(ROLE_MANAGER));
                roleService.create(new RoleDto(ROLE_ADMIN));
                roleService.create(new RoleDto(ROLE_SUPER_ADMIN));
            }
            if (userService.findAll().isEmpty()) {
                userService.create(new UserDto("Jeanpier Mendoza", "jeanpi3rm@gmail.com", "jeanpierm", "314159"));
                userService.create(new UserDto("Arnold Schwarzenegger", "arnold@email.com", "arnold", "12345"));
                userService.create(new UserDto("Emily Lino", "emily@email.com", "emily", "12345"));
                userService.create(new UserDto("Kaleb Chara", "kaleb@email.com", "kaleb", "12345"));

                userService.addRoleByUsername("jeanpierm", ROLE_USER);
                userService.addRoleByUsername("jeanpierm", ROLE_MANAGER);
                userService.addRoleByUsername("jeanpierm", ROLE_ADMIN);
                userService.addRoleByUsername("emily", ROLE_MANAGER);
                userService.addRoleByUsername("kaleb", ROLE_ADMIN);
                userService.addRoleByUsername("arnold", ROLE_SUPER_ADMIN);
                userService.addRoleByUsername("arnold", ROLE_ADMIN);
                userService.addRoleByUsername("arnold", ROLE_USER);
            }
        };
    }
}
