package br.com.fiap.ja.odontoprev.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**").permitAll()
                        .requestMatchers("/actuator/prometheus").permitAll()
                        .requestMatchers("/actuator/**").authenticated()
                        .requestMatchers("/profissional", "/profissional/novo",
                                "/profissional/editar/**", "/pacientes",
                                "/pacientes/novo", "/pacientes/editar/**").hasRole("USER")
                        .requestMatchers("/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/pacientes", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("usuario1")
                .password(passwordEncoder().encode("senha1"))
                .roles("USER")
                .build();
        UserDetails user2 = User.builder()
                .username("usuario2")
                .password(passwordEncoder().encode("senha2"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}