package com.monds.demos;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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

//    @Autowired
//    private UserDetailsService userDetailsService;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).
//                passwordEncoder(new ShaPasswordEncoder(encodingStrength));
//    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "**").permitAll()
                .and()
                //                .csrf()
//                .ignoringAntMatchers("/h2-console/**")
//                .disable()
//                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
//                .and()
//                .disable()
                .headers()
                    .frameOptions()
                    .disable()
                    .and()
                .formLogin()
                    .permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public FilterRegistrationBean corsFilterRegistrationBean() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.applyPermitDefaultValues();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setExposedHeaders(Arrays.asList("content-length"));
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
