package com.getir.readingisgood.security;

import com.getir.readingisgood.models.RoleType;
import com.getir.readingisgood.security.jwt.EntryPoint;
import com.getir.readingisgood.security.jwt.TokenFilter;
import com.getir.readingisgood.security.service.GetirUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Order(0)
@Log4j2
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        private final GetirUserDetailService getirUserDetailService;
        private final EntryPoint unauthorizedPoint;
        private final TokenFilter tokenFilter;

        private static final String[] WHITELIST = {
                // -- Swagger UI v2
                "/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/webjars/**",
                // -- Swagger UI v3 (OpenAPI)
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-ui/**",

                "/api/auth/**"
        };

        private static final String[] ADMIN_WHITELIST = {
                "/api/book/**",
                "/api/statistics/**"
        };

        @Override
        public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
                authenticationManagerBuilder.userDetailsService(getirUserDetailService).passwordEncoder(passwordEncoder());
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http.cors().and().csrf().disable()
                        .exceptionHandling()
                        .authenticationEntryPoint(unauthorizedPoint)
                        .and()
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .authorizeRequests()
                        .antMatchers(WHITELIST).permitAll()
                        .antMatchers(ADMIN_WHITELIST).hasAnyAuthority(RoleType.ADMIN.getName())
                        .anyRequest().authenticated()
                        .and()
                        .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        }

}
