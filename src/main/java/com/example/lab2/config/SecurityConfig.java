package com.example.lab2.config;

import com.example.lab2.model.Permission;
import com.example.lab2.model.Role;
import com.example.lab2.security.jwt.JwtConfigurer;
import com.example.lab2.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String LOGIN_ENDPOINT = "/login";
    private static final String CREATE_ENDPOINT = "/create";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/**.js", "/**.html", "/**.css", "/**/**.html").permitAll()
                .antMatchers("*.html").permitAll()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(CREATE_ENDPOINT).permitAll()
//                .antMatchers("/notes**").hasAuthority(Role.USER.getAuthorities().toString())
//                .antMatchers("/notes/**").hasRole(Role.USER.toString())
//                .antMatchers("/notes").hasRole(Role.USER.toString())
                .antMatchers(HttpMethod.GET,"/notes").hasAuthority(Permission.GET_SELF_NOTES.getPermission())
                .antMatchers(HttpMethod.GET,"/notes/*").hasAuthority(Permission.GET_SELF_NOTE.getPermission())
                .antMatchers(HttpMethod.POST,"/notes").hasAuthority(Permission.ADD_SELF_NOTE.getPermission())
                .antMatchers(HttpMethod.DELETE,"/notes/*").hasAuthority(Permission.DELETE_SELF_NOTE.getPermission())
                .antMatchers(HttpMethod.PUT,"/notes/*").hasAuthority(Permission.EDIT_SELF_NOTE.getPermission())
//                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
