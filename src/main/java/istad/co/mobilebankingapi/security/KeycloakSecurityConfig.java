package istad.co.mobilebankingapi.security;

import istad.co.mobilebankingapi.enums.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class KeycloakSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;


    @Bean
    public DaoAuthenticationProvider daoAuthProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }



    @Bean
    public SecurityFilterChain configureApiSecurity(HttpSecurity http) throws Exception {
        // TODO
        // Make all endpoints secured
        http.authorizeHttpRequests(endpoints -> endpoints
                .requestMatchers(HttpMethod.GET,"/api/v1/customers/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER")
                .requestMatchers(HttpMethod.POST,"/api/v1/customers/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/api/v1/customers/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/v1/customers/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/accounts/**").hasAnyRole("ADMIN", "STAFF", "CUSTOMER")
                .requestMatchers("/media/**").permitAll()
                .requestMatchers("/api/v1/medias/**").permitAll()
                .anyRequest()
                .authenticated()

        );

        // Disable form login of web
        http.formLogin(form -> form.disable());

        // Set security mechanism = Oauth2
        http.oauth2ResourceServer(oauth2->oauth2
                .jwt(Customizer.withDefaults()));

        // CSRF common protection ->  CSRF generate token (Spring Security)
        http.csrf(token -> token.disable());

        // Make stateless API
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
            Map<String,Collection<String>> realmAccess = jwt.getClaim("realm_access");
            Collection<String> roles = realmAccess.get("roles");
            return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+ role)).collect(Collectors.toList());
        };
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return converter;
    }


}
