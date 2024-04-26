package com.guidopierri.pantrybe.config;

import com.guidopierri.pantrybe.permissions.Roles;
import com.guidopierri.pantrybe.services.SpringSecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final PasswordConfig passwordConfig;
    private final SpringSecurityUserDetailsService springSecurityUserDetailsService;

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Autowired
    public SecurityConfig(PasswordConfig passwordConfig, SpringSecurityUserDetailsService springSecurityUserDetailsService) {
        this.passwordConfig = passwordConfig;
        this.springSecurityUserDetailsService = springSecurityUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .oauth2ResourceServer((oauth2ResourceServer) ->
                        oauth2ResourceServer
                                .jwt((jwt) ->
                                        jwt.decoder(jwtDecoder())
                                )
                )
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login","logout","/register-test", "/api/v1/users/create", "/api/v1/pantry/create-pantry").permitAll()
                        .requestMatchers("/admin").hasRole(Roles.ADMIN.toString())
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe
                        //.rememberMeCookieName("remember-me")
                        .rememberMeParameter("remember-me")
                        .key("remember-me-key")
                        .tokenValiditySeconds(24 * 60 * 60)
                        .userDetailsService(springSecurityUserDetailsService)
                )
                .logout(logout -> logout
                        //.logoutUrl("/logout")
                        .logoutUrl("/perform_logout")
                        //.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .logoutSuccessUrl("/login"))
                .authenticationProvider(daoAuthenticationProvider())    // Tell Spring to use our implementation (Password & Service)
                .build();
    }
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordConfig.passwordEncoder());
        provider.setUserDetailsService(springSecurityUserDetailsService);
        provider.setAuthoritiesMapper(authorities -> authorities);

        return provider;
    }
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }


}