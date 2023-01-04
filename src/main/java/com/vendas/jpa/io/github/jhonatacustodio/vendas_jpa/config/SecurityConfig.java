package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService usuarioService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager configure(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
            auth.userDetailsService(usuarioService)
            .passwordEncoder(passwordEncoder());
        return auth.build();
    }
    
    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
            .authorizeHttpRequests().requestMatchers("/api/clientes/**")
                .hasAnyRole("USER", "ADMIN")
            .requestMatchers("/api/produtos/**")
                .hasRole("ADMIN")
            .requestMatchers("/api/pedidos/**")
                .hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/usuarios/**")
                .permitAll().anyRequest().authenticated()
            .and()
            .httpBasic();

		return http.build();
	}
    
}
