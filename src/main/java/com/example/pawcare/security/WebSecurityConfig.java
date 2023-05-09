package com.example.pawcare.security;

import com.example.pawcare.entities.Roles;
import com.example.pawcare.security.jwt.JwtAuthenticationFilter;
import com.example.pawcare.security.jwt.JwtEntryPoint;
import com.example.pawcare.services.user.UserServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig  {


    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    private JwtEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter authenticationJwtTokenFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userServiceImp);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
          http.cors()
                  .and()
                  .csrf()
                  .disable()
                  .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
              .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                  .antMatchers("/hotel/**").permitAll()
                  .antMatchers("/reservation/**").permitAll()
                  .antMatchers("/api/users/**").permitAll()
                  .antMatchers("/order/**").permitAll()
                  .antMatchers("/accessory/**").permitAll()
                  .antMatchers("/stripe/**").permitAll()
                  .antMatchers("/cart/**").permitAll()
                  .antMatchers("/pawcareupload/**").permitAll()
                  .antMatchers("/accessories/**").permitAll()
                  .antMatchers("/listaccessories/**").permitAll()
                .antMatchers("/addAccessoryUpload1/**").permitAll()

                  .anyRequest().authenticated();

         http.authenticationProvider(authenticationProvider());

          http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

         return http.build();
       }



    /** public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http//.cors().and().csrf().disable()
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/users/**")//.permitAll()
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic().and().csrf().disable();
                //.anyRequest().authenticated();

        http.authenticationProvider(authenticationProvider());


        return http.build();
    }**/
}
