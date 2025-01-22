package org.example.exercice10_flux.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                .pathMatchers(HttpMethod.DELETE, "/api/rooms").hasRole("ADMIN")
                .pathMatchers(HttpMethod.POST, "/api/rooms").hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET, "/api/rooms/**").hasAnyRole("ADMIN", "USER")
                .anyExchange().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(unauthorizedHandler()) // Custom 401 handler
                .and()
                .exceptionHandling()
                .accessDeniedHandler(forbiddenHandler()) // Custom 403 handler
                .and()
                .csrf().disable()
                .build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("john")
                .password("doe")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("jeanne")
                .password("doe")
                .roles("ADMIN")
                .build();

        return new MapReactiveUserDetailsService(user, admin);
    }


    @Bean
    public ServerAuthenticationEntryPoint unauthorizedHandler() {
        return (exchange, exception) -> {
            String responseBody = """
                    {
                        "error": "UNAUTHORIZED",
                        "message": "Vous devez vous connecter pour accéder à cette ressource"
                    }
                    """;
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().add("Content-Type", "application/json");
            byte[] bytes = responseBody.getBytes(StandardCharsets.UTF_8);
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
        };
    }

    @Bean
    public ServerAccessDeniedHandler forbiddenHandler() {
        return (exchange, exception) -> {
            String responseBody = """
                    {
                        "error": "FORBIDDEN",
                        "message": "Vous n'avez pas les droits nécessaires pour accéder à cette ressource"
                    }
                    """;
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            exchange.getResponse().getHeaders().add("Content-Type", "application/json");
            byte[] bytes = responseBody.getBytes(StandardCharsets.UTF_8);
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
        };
    }
}
