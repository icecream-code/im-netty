package com.iqy.im.security;

import com.iqy.im.security.filter.AuthenticationFilter;
import com.iqy.im.security.filter.AuthorizationFilter;
import com.iqy.im.security.handler.ForbiddenHandler;
import com.iqy.im.security.handler.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(securityUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginProcessingUrl("/login")
                .permitAll()

                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()

                .and()
                .authorizeRequests()
                .antMatchers("/user/register/**", "/index.html", "/static/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .addFilter(new AuthenticationFilter(authenticationManager(), jwtUtil))
                .addFilter(new AuthorizationFilter(authenticationManager(), jwtUtil))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedEntryPoint())
                .accessDeniedHandler(new ForbiddenHandler())

                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
