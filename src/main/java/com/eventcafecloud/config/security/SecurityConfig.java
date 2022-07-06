package com.eventcafecloud.config.security;

import com.eventcafecloud.config.properties.AppProperties;
import com.eventcafecloud.config.properties.CorsProperties;
import com.eventcafecloud.oauth.exception.RestAuthenticationEntryPoint;
import com.eventcafecloud.oauth.filter.TokenAuthenticationFilter;
import com.eventcafecloud.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.eventcafecloud.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.eventcafecloud.oauth.handler.TokenAccessDeniedHandler;
import com.eventcafecloud.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.eventcafecloud.oauth.service.CustomOAuth2UserService;
import com.eventcafecloud.oauth.service.CustomUserDetailsService;
import com.eventcafecloud.oauth.token.AuthTokenProvider;
import com.eventcafecloud.user.domain.type.RoleType;
import com.eventcafecloud.user.repository.UserRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    //UserDetailService 설정 (직접 로그인)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  //인증방법을 의미하는 메소드
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //JWT 인증에는 기본적으로 session을 사용하지 않기 때문에 STATELESS
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(tokenAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                .antMatchers("/**").permitAll()   //권한관리대상을 지정, URL, HTTP 메소드별로 관리 가능
                .antMatchers("/register/**").hasAnyAuthority(RoleType.GUEST.getCode())
                .antMatchers("/admin/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/events/**").permitAll()
                .antMatchers("/cafes/**").permitAll()
                .antMatchers("/post/**").permitAll()
                .antMatchers("/board/**").permitAll()
                .antMatchers("/api/**").hasAnyAuthority(RoleType.GUEST.getCode())
                .antMatchers("/api/**/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
                .anyRequest().authenticated() //설정된 값 이외의 나머지 URL, 인증된 사용자, 로그인한 사용자만 볼 수 있음
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()  //Oauth2 로그인 기능에대한 여러가지 설정의 진입점
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository(oAuth2AuthorizationRequestbasedOnCookieRepository()) //Authorization request와 관련된 state가 저장됨
                .and()
                .redirectionEndpoint()//endpoint로 인증요청을 받으면, Spring securiy의 Oauth2 사용자를 provider가 제공하는 AuthorizationUri로 Redirect
                .baseUri("/*/oauth2/code/*") // 이 때, 사용자 인증코드 (authorization code)를 함께 갖고감
                .and()
                .userInfoEndpoint() //Oauth2 로그인 성공 이후 사용자 정보를 가져올때의 설정 담당
                .userService(oAuth2UserService) // 소셜 로그인 성공 시 후속조치를 진행할 UserService인터페이스의 구현체 등록
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler()) // JWT authentication token을 만들고, client가 정의한 redirect로 token을 갖고 넘어감
                .failureHandler(oAuth2AuthenticationFailureHandler()); // 인증이 실패하면 error코드를 담은 uri를 넘겨줌

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    //auth 매니저 설정
    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    //토큰 필터 설정
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    //Oauth 인증 실패 핸들러
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestbasedOnCookieRepository());
    }

    //Oauth 인증 성공 핸들러
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperties,
                userRefreshTokenRepository,
                oAuth2AuthorizationRequestbasedOnCookieRepository()
        );
    }

    //쿠키 기반 인가 repository, 인가 응답을 연계 하고 검증할 때 사용
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestbasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    //Security 설정 시, 사용할 인코더 설정
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Cors 설정
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }
}
