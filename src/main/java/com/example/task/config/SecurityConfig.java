package com.example.task.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.example.task.domain.auth.CustomAuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/restProgress/**"
                        ))


                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()  // 静的リソースの許可                        .requestMatchers("/task/**").authenticated()
                        .requestMatchers("/progress/**").authenticated()
                        .requestMatchers("/user/login").permitAll()
                        .requestMatchers("/health", "/public", "/actuator/health").permitAll()  // ヘルスチェック用
                                .requestMatchers("/user/userRegist").permitAll()
                //        .requestMatchers("/user/**").hasRole("ADMIN")
                        .requestMatchers("/api/**").permitAll() // 特定のエンドポイントへのアクセスを許可
                        .requestMatchers("/**").authenticated()
                        .anyRequest().authenticated()
                )
                // CORSの設定を適用
                .cors(customizer -> customizer.configurationSource(corsConfigurationSource()))
                .formLogin(login -> login
                        .loginProcessingUrl("/user/login")
                        .loginPage("/user/login")
                        .successHandler(customAuthenticationSuccessHandler)  // 成功ハンドラーの設定
                        .failureUrl("/user/login?error=true")
                        //.defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .logoutSuccessUrl("/user/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                ).headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("frame-ancestors 'self' https://various11project.com")
                        )
                        .frameOptions(frameOptions -> frameOptions
                                .sameOrigin() // X-Frame-Optionsを無効化
                        ));

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        // CORSの設定を行うためのオブジェクトを生成
        CorsConfiguration configuration = new CorsConfiguration();

        // クレデンシャル（資格情報（CookieやHTTP認証情報））を含むリクエストを許可する
        configuration.setAllowCredentials(true);

        // 許可するオリジンを設定
        configuration.addAllowedOrigin("http://localhost:3000");

        // 任意のヘッダーを許可
        configuration.addAllowedHeader("*");

        // 任意のHTTPメソッド（GET, POSTなど）を許可
        configuration.addAllowedMethod("*");

        // CORS設定をURLベースで行うためのオブジェクトを生成
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 全てのURLパスにこのCORS設定を適用
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
