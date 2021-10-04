package com.softwarejm.demojava17;

import com.softwarejm.demojava17.model.Role;
import com.softwarejm.demojava17.model.User;
import com.softwarejm.demojava17.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.softwarejm.demojava17.config.Names.*;

@SpringBootApplication
public class DemoJava17Application {

	public static void main(String[] args) {
		SpringApplication.run(DemoJava17Application.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, ROLE_USER));
			userService.saveRole(new Role(null, ROLE_MANAGER));
			userService.saveRole(new Role(null, ROLE_ADMIN));
			userService.saveRole(new Role(null, ROLE_SUPER_ADMIN));

			userService.saveUser(new User(null, "Jeanpier Mendoza", "jeanpi3rm@gmail.com", "12345"));
			userService.saveUser(new User(null, "Arnold Schwarzenegger", "arnold@email.com", "12345"));
			userService.saveUser(new User(null, "Emily Lino ", "emily@email.com", "12345"));
			userService.saveUser(new User(null, "Kaleb Chara", "kaleb@email.com", "12345"));

			userService.addRoleToUser("jeanpi3rm@gmail.com", ROLE_USER);
			userService.addRoleToUser("jeanpi3rm@gmail.com", ROLE_MANAGER);
			userService.addRoleToUser("emily@email.com", ROLE_MANAGER);
			userService.addRoleToUser("kaleb@email.com", ROLE_ADMIN);
			userService.addRoleToUser("arnold@email.com", ROLE_SUPER_ADMIN);
			userService.addRoleToUser("arnold@email.com", ROLE_ADMIN);
			userService.addRoleToUser("arnold@email.com", ROLE_USER);
		};
	}
}
