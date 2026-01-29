package ru.altaiensb.service_desk.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                /* Включить, когда появиться авторизация
                 * .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                 * .authorizeHttpRequests(auth -> auth
                 * .requestMatchers("/login", "/register", "/css/**").permitAll()
                 * .anyRequest().authenticated())
                 * .formLogin(form -> form
                 * .loginPage("/login")
                 * .defaultSuccessUrl("/home", true)
                 * .permitAll())
                 * .logout(logout -> logout
                 * .logoutSuccessUrl("/login/logout")
                 * .permitAll())
                 * .csrf(csrf -> csrf.disable());
                 */
                // ← Включаем CORS (обязательно здесь!)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // ← Основная часть: правила доступа
                .authorizeHttpRequests(auth -> auth
                        // Разрешаем всем (даже не залогиненным) доступ к типам заявок
                        .requestMatchers("/api/**").permitAll()

                        // Или сразу все API (если пока тестируешь)
                        // .requestMatchers("/api/**").permitAll()

                        // Всё остальное требует аутентификации
                        .anyRequest().authenticated())

                // Отключаем CSRF для REST API (обычно безопасно, если нет форм)
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
