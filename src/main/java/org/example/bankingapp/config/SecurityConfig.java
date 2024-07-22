package org.example.bankingapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/accounts/[**").hasRole("USER")
                .antMatchers("/api/admin/**](https://www.bing.com/search?form=SKPBOT&q=%26quot%3B%29.hasRole%28%26quot%3BUSER%26quot%3B%29%0D%0A.antMatchers%28%26quot%3B%2Fapi%2Fadmin%2F)").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("Serg")
                .password(new BCryptPasswordEncoder().encode("5678"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("Alex")
                .password(new BCryptPasswordEncoder().encode("4321"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("Den")
                .password(new BCryptPasswordEncoder().encode("1234"))
                .roles("ADMIN")
                .build());
        return manager;
    }
}