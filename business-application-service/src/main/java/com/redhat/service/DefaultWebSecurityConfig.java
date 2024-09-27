package com.redhat.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration("kieServerSecurity")
@EnableWebSecurity
public class DefaultWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* Default
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                  .antMatchers("/rest/*").authenticated().and()
                .httpBasic().and()
                .headers().frameOptions().disable();
        */
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                  .antMatchers("/rest/**").authenticated().and()
                .httpBasic().and()
                .headers().frameOptions().disable();
        /* To authorize requests for access by an authenticated user only
        http
            .cors().and().csrf().disable()
            .authorizeRequests()
                .antMatchers("/**").hasRole("<role>")
                .anyRequest().authenticated()
            .and().httpBasic()
            .and().headers().frameOptions().disable();
        */
        /* To authorize a user that has one of a range of roles
        http
            .cors().and().csrf().disable()
            .authorizeRequests()
                .antMatchers("/**").hasAnyRole("<role>", "<role1")
                .anyRequest().authenticated()
            .and().httpBasic()
            .and().headers().frameOptions().disable();
        */
        /* configure Spring Security in a Red Hat Process Automation Manager business application to provide the security context without authentication
        http
            .cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers("/*")
                .permitAll()
            .and().headers().frameOptions().disable();
        */
        /* If you disable Spring Security authentication by using the PermitAll method, any user can log in to the application, but users will have limited access and functionality. However, you can preauthenticate a user
        http
            .anonymous().authenticationFilter(new AnonymousAuthFilter()).and() // Override anonymousUser
            .cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers("/*").permitAll()
            .and().headers().frameOptions().disable();        
        */
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication().withUser("user").password("user").roles("kie-server");
        auth.inMemoryAuthentication().withUser("wbadmin").password("wbadmin").roles("admin");
        auth.inMemoryAuthentication().withUser("kieserver").password("kieserver1!").roles("kie-server");
        auth.inMemoryAuthentication().withUser("executionUser").password("executionUser").roles("kie-server").roles("rest-all").roles("hr");
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.HEAD.name(),
                                                          HttpMethod.POST.name(), HttpMethod.DELETE.name(), HttpMethod.PUT.name()));
        corsConfiguration.applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
