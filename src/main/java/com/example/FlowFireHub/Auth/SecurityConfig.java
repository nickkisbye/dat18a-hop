package com.example.FlowFireHub.Auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtAuthFilter jwtAuthenticationFilter() {
        return new JwtAuthFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                cors().and()
                .authorizeRequests()
                .antMatchers("/token")
                .permitAll()
                .and().authorizeRequests().antMatchers("/me").access("hasAuthority('Administrator') or hasAuthority('Bruger')")
                .and().authorizeRequests().antMatchers("/friend/**").access("hasAuthority('Administrator') or hasAuthority('Bruger')")
                .and().authorizeRequests().antMatchers("/flowfire/**" ).access("hasAuthority('Administrator') or hasAuthority('Bruger')")
                .and().authorizeRequests().antMatchers("/admin/**").hasAuthority("Administrator")
                .and().authorizeRequests().antMatchers("/users/**").access("hasAuthority('Administrator') or hasAuthority('Bruger')")
                .and().authorizeRequests().antMatchers("/chatroom/**").access("hasAuthority('Administrator') or hasAuthority('Bruger')")
                .and().authorizeRequests().antMatchers("/ws/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .addFilterBefore(jwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);
    }

//    @Override
//    public void configure(WebSecurity http) throws Exception {
//        http
//                .ignoring()
//                .antMatchers("/h2-console/**");
//    }
}