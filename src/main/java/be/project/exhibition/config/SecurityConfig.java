package be.project.exhibition.config;

import be.project.exhibition.filter.JwtTokenFilter;
import be.project.exhibition.exception.CustomAuthenticationEntryPoint;
import be.project.exhibition.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    @Value("${jwt.secret-key}")
    private String key;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                    .requestMatchers("/api/*/users/join", "/api/*/users/login", "/api/*/posts/{postId}","/api/v1/posts", "/").permitAll()
                    .requestMatchers("/api/*/users/**", "api/v1/posts/**").authenticated()
                    .requestMatchers("/api/**").authenticated()
                    .requestMatchers("/swagger-ui/**", "/v1/api-docs/**").permitAll()
                    .anyRequest().permitAll()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(new JwtTokenFilter(key,userService), UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling()
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    private static final String[] WHITE_LIST = {
            "/api/*/users/join", "/api/*/users/login", "/api/*/posts/all"
    };
}
