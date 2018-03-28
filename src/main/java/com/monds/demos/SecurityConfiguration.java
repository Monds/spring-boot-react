package com.monds.demos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity // 정말 꼭 필요한건가?? 이게 있어야 default authentication을 해제한다고 하는데 httpBasic disable 로 다 해결되었다.
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Value("${security.signing-key}")
//    private String signingKey;
//
//    @Value("${security.encoding-strength}")
//    private Integer encodingStrength;
//
//    @Value("${security.security-realm}")
//    private String securityRealm;

    @Autowired
    public DataSource dataSource;

    // 반드시 autowired 를 해줘야 다른 클래스에서 참조가 가능하다.
    // autowired 를 하지 않을 경우 SecurityConfiguration 에서는 정상적으로 생성했을지 몰라도
    // 해당 authenticationManager 를 사용하는 다른 클래스는 inmemory 방식으로 실행되어 사용자 정보를 조회하지 못한다.

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource);
        // users, authorities 테이블 생성을 위해 1회에 한하여 withDefaultSchema() 를 선언한다.
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .and()
                .headers()
                    .frameOptions()
                    .disable()
                    .and()
                .formLogin()
                    .permitAll()
                .and()
                .csrf().disable();
    }

//    @Bean
//    public FilterRegistrationBean corsFilterRegistrationBean() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.applyPermitDefaultValues();
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(Arrays.asList("*"));
//        config.setAllowedHeaders(Arrays.asList("*"));
//        config.setAllowedMethods(Arrays.asList("*"));
//        config.setExposedHeaders(Arrays.asList("content-length"));
//        config.setMaxAge(3600L);
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(0);
//        return bean;
//    }

}
