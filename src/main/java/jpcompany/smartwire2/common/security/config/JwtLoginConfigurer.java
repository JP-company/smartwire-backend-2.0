package jpcompany.smartwire2.common.security.config;

import jpcompany.smartwire2.common.security.filter.JwtAuthenticationFilter;
import jpcompany.smartwire2.common.security.filter.JwtAuthorizationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public final class JwtLoginConfigurer<H extends HttpSecurityBuilder<H>>
        extends AbstractAuthenticationFilterConfigurer<H, JwtLoginConfigurer<H>, JwtAuthenticationFilter>
{
    private AuthenticationSuccessHandler successHandler;
    private AuthenticationFailureHandler failureHandler;
    private AuthenticationManager authenticationManager;
    private JwtAuthorizationFilter authorizationFilter;

    public JwtLoginConfigurer() {
        super(new JwtAuthenticationFilter(), null);
    }

    @Override
    public void init(H http) throws Exception {
        super.init(http);
    }

    @Override
    public void configure(H http) {
        if(authenticationManager == null){
            authenticationManager = http.getSharedObject(AuthenticationManager.class);
        }
        // 생성자에서 등록한 인증필터 불러옴
        getAuthenticationFilter().setAuthenticationManager(authenticationManager);
        getAuthenticationFilter().setAuthenticationSuccessHandler(successHandler);
        getAuthenticationFilter().setAuthenticationFailureHandler(failureHandler);

        SessionAuthenticationStrategy sessionAuthenticationStrategy = http
                .getSharedObject(SessionAuthenticationStrategy.class);

        if (sessionAuthenticationStrategy != null) {
            getAuthenticationFilter().setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        }

        http.setSharedObject(JwtAuthenticationFilter.class, getAuthenticationFilter());
        http.addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    public JwtLoginConfigurer<H> setSuccessHandlerJwt(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
        return this;
    }

    public JwtLoginConfigurer<H> setFailureHandlerJwt(AuthenticationFailureHandler authenticationFailureHandler) {
        this.failureHandler = authenticationFailureHandler;
        return this;
    }

    public JwtLoginConfigurer<H> setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        return this;
    }

    public JwtLoginConfigurer<H> setAuthorizationFilter(JwtAuthorizationFilter authorizationFilter) {
        this.authorizationFilter = authorizationFilter;
        return this;
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, "POST");
    }
}
