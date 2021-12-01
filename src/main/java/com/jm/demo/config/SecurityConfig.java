package com.jm.demo.config;

import com.jm.demo.auth.JwtAuthenticationFilter;
import com.jm.demo.auth.JwtAuthorizationFilter;
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

import static com.jm.demo.constants.Names.ROLE_USER;
import static com.jm.demo.constants.Paths.*;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ROOT_ENTRY_POINT = "/";
    private static final String SWAGGER_UI_ENTRY_POINT = SWAGGER_UI_PATH + "/**";
    private static final String SWAGGER_HTML_ENTRY_POINT = SWAGGER_UI_PATH + ".html/**";
    private static final String SWAGGER_RESOURCES_ENTRY_POINT = SWAGGER_RESOURCES_PATH + "/**";
    private static final String API_DOCS_ENTRY_POINT = API_DOCS_PATH + "/**";
    private static final String LOGIN_ENTRY_POINT = LOGIN_PATH + "/**";
    private static final String REFRESH_TOKEN_ENTRY_POINT = REFRESH_TOKEN_PATH + "/**";
    private static final String USERS_ENTRY_POINT = USERS_PATH + "/**";
    private static final String ERROR_ENTRY_POINT = "/error";

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(authenticationManagerBean(), userService);
        JwtAuthorizationFilter authorizationFilter = new JwtAuthorizationFilter();

        authenticationFilter.setFilterProcessesUrl(LOGIN_PATH);

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http
                .addFilter(authenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(
                        ROOT_ENTRY_POINT,
                        API_DOCS_ENTRY_POINT,
                        SWAGGER_UI_ENTRY_POINT,
                        SWAGGER_HTML_ENTRY_POINT,
                        SWAGGER_RESOURCES_ENTRY_POINT,
                        LOGIN_ENTRY_POINT,
                        REFRESH_TOKEN_ENTRY_POINT,
                        ERROR_ENTRY_POINT
                ).permitAll()
                .antMatchers(POST, USERS_ENTRY_POINT).permitAll() // permit register
                .antMatchers(GET, USERS_ENTRY_POINT).hasAnyAuthority(ROLE_USER)
                .anyRequest().authenticated();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
