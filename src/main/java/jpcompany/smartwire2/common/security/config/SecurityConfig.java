package jpcompany.smartwire2.common.security.config;

import jpcompany.smartwire2.common.security.filter.JwtAuthorizationFilter;
import jpcompany.smartwire2.common.security.handler.CustomAccessDeniedHandler;
import jpcompany.smartwire2.common.security.handler.CustomUnAuthenticationHandler;
import jpcompany.smartwire2.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/error-test").permitAll()
                                .requestMatchers("/login", "/join/**", "/email_verify/**").anonymous()
                                .requestMatchers("/", "/api/**").hasAuthority(Member.Role.MEMBER.name())
                                .requestMatchers("/admin/**").hasAuthority(Member.Role.ADMIN.name())
                                .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionConfig ->
                        exceptionConfig
                                .authenticationEntryPoint(new CustomUnAuthenticationHandler())
                                .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .apply(new JwtLoginConfigurer<>())
                .setSuccessHandlerJwt(authenticationSuccessHandler)
                .setFailureHandlerJwt(authenticationFailureHandler)
                .setAuthenticationManager(authenticationManager(authenticationConfiguration))
                .setAuthorizationFilter(jwtAuthorizationFilter)
                .loginProcessingUrl("/login");

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        ProviderManager authenticationManager = (ProviderManager)authenticationConfiguration.getAuthenticationManager();
        authenticationManager.getProviders().add(authenticationProvider);
        return authenticationManager;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // 아예 보안 필터를 거치지 않음
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

}