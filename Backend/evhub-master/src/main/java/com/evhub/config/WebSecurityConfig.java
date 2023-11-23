package com.evhub.config;

import com.evhub.constants.AppsConstants;
import com.evhub.security.JWTAuthenticationFilter;
import com.evhub.security.JWTLoginFilter;
import com.evhub.service.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    private SecurityService securityService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry securityConfig = http
                .csrf().disable()
                .authorizeRequests()

                .antMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                .antMatchers(HttpMethod.POST, "/api/user/registerUser").permitAll()

                .antMatchers(HttpMethod.GET, "/api/user/getAllUsers").hasAuthority(
                        AppsConstants.UserRole.ADMIN.getDescription())

                .antMatchers(HttpMethod.DELETE, "/api/user/deleteUser/{userID}").hasAuthority(
                        AppsConstants.UserRole.ADMIN.getDescription())

                .antMatchers(HttpMethod.POST, "/api/charging-station/saveChargingStation").hasAuthority(
                        AppsConstants.UserRole.VENDOR.getDescription())

                .antMatchers(HttpMethod.POST, "/api/charging-station/updateChargingStation/{chargingStationID}").hasAnyAuthority(
                        AppsConstants.UserRole.VENDOR.getDescription(), AppsConstants.UserRole.OWNER.getDescription())

                .antMatchers(HttpMethod.DELETE, "/api/charging-station/deleteChargingStation/{chargingStationID}").hasAnyAuthority(
                        AppsConstants.UserRole.VENDOR.getDescription())

                .antMatchers(HttpMethod.POST, "/api/transaction/addTransaction").hasAnyAuthority(
                        AppsConstants.UserRole.OWNER.getDescription(), AppsConstants.UserRole.USER.getDescription());

        securityConfig.anyRequest().authenticated();

        securityConfig
                .and()
                .addFilterBefore(new JWTAuthenticationFilter(securityService),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTLoginFilter("/api/auth/login", authenticationManager(), securityService),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
