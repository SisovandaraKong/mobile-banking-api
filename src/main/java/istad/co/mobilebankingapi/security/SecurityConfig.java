package istad.co.mobilebankingapi.security;

import istad.co.mobilebankingapi.enums.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
//
//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("qwer1234"))
//                .roles("ADMIN")
//                .build();
//        manager.createUser(admin);
//
//        UserDetails staff = User.builder()
//                .username("staff")
//                .password("staff")
//                .roles("STAFF")
//                .build();
//        manager.createUser(staff);
//
//        UserDetails customer = User.builder()
//                .username("customer")
//                .password("customer")
//                .roles("CUSTOMER")
//                .build();
//        manager.createUser(customer);
//        return manager;
//    }

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
