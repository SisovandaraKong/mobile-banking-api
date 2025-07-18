package istad.co.mobilebankingapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configureApiSecurity(HttpSecurity http) throws Exception {
        // TODO
        // Make all endpoints secured
        http.authorizeHttpRequests(endpoints -> endpoints
                .anyRequest()
                .authenticated()
        );

        // Disable form login of web
        http.formLogin(form -> form.disable());

        // Set security mechanism = HTTP Basic Authentication => JWT, Oauth2
        http.httpBasic(Customizer.withDefaults());

        // CSRF common protection ->  CSRF generate token (Spring Security)
        http.csrf(token -> token.disable());

        // Make stateless API
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
