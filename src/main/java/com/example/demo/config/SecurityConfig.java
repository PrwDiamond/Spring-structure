package com.example.demo.config;

import com.example.demo.config.token.TokenFilterConfigurer;
import com.example.demo.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenService tokenService;

    private final String[] PUBLIC = {
            "/actuator/**",
            "/user/register",
            "/user/login",
            "/user/activate",
            "/user/resend-activation-email",
            "/socket/**"
    };

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(config -> {

            CorsConfiguration cors = new CorsConfiguration();
            cors.setAllowCredentials(true);
            cors.setAllowedOriginPatterns(Collections.singletonList("http://*"));
            cors.addAllowedHeader("*");
            cors.addAllowedMethod("OPTIONS");
            cors.addAllowedMethod("POST");
            cors.addAllowedMethod("GET");
            cors.addAllowedMethod("PUT");
            cors.addAllowedMethod("DELETE");

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", cors);

            config.configurationSource(source);
        }).csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers(PUBLIC).anonymous()
                .antMatchers("/product/{id}").hasAnyAuthority("user")
                .anyRequest().authenticated()
                .and().apply(new TokenFilterConfigurer(tokenService));
    }



}
