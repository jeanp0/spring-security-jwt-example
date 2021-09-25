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

			userService.saveUser(new User(null, "Jeanpier Mendoza", "jeanpierm", "12345"));
			userService.saveUser(new User(null, "Arnold Schwarzenegger", "arnold", "12345"));
			userService.saveUser(new User(null, "Emily Lino ", "emily", "12345"));
			userService.saveUser(new User(null, "Kaleb Chara", "kaleb", "12345"));

			userService.addRoleToUser("jeanpierm", ROLE_USER);
			userService.addRoleToUser("jeanpierm", ROLE_MANAGER);
			userService.addRoleToUser("emily", ROLE_MANAGER);
			userService.addRoleToUser("kaleb", ROLE_ADMIN);
			userService.addRoleToUser("arnold", ROLE_SUPER_ADMIN);
			userService.addRoleToUser("arnold", ROLE_ADMIN);
			userService.addRoleToUser("arnold", ROLE_USER);
		};
	}
}
