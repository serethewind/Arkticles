package com.serethewind.Arkticles.securityConfig;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.net.http.HttpRequest;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private CustomUserDetailsService customUserDetailsService;
    private JwtAuthEntryPoint authEntryPoint;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling.authenticationEntryPoint(authEntryPoint))

                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers(HttpMethod.POST, "/api/arkticles/v1/auth/**").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/api/arkticles/v1/users/home").permitAll();
                    authorize.requestMatchers("/api/arkticles/v1/users/**").hasAuthority("ADMIN");
                    authorize.requestMatchers("/api/arkticles/v1/posts/**").hasAnyAuthority("ADMIN", "USER");
                    authorize.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());
        return http.build();
    }

}

