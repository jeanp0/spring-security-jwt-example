package com.jm.demo.config.security;

import com.jm.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.jm.demo.config.constants.Names.ROLE_ADMIN;
import static com.jm.demo.config.constants.Names.ROLE_USER;
import static com.jm.demo.config.constants.Paths.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), userService);
        CustomAuthorizationFilter authorizationFilter = new CustomAuthorizationFilter();

        authenticationFilter.setFilterProcessesUrl(LOGIN_PATH);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        // Autorizado para todos
        http.authorizeRequests().antMatchers(
                "/**",
                API_DOCS_PATH + "/**",
                SWAGGER_UI_PATH + "/**",
                SWAGGER_RESOURCES_PATH + "/**",
                LOGIN_PATH + "/**",
                REFRESH_TOKEN_PATH + "/**"
        ).permitAll();
        // Autorizado para rol User
        http.authorizeRequests().antMatchers(GET, USERS_PATH + "/**").hasAnyAuthority(ROLE_USER);
        // Autorizado para rol Admin
        http.authorizeRequests().antMatchers(POST, USERS_PATH + "/**").hasAnyAuthority(ROLE_ADMIN);
        // Cualquier petición restante requiere estar autenticado
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
