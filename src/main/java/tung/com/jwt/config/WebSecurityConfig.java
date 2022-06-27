package tung.com.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tung.com.jwt.service.impl.UserAuthService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  UserAuthService service;

//  @Bean
//  public JwtAuthenticationFilter jwtAuthenticationFilter() {
//    return new JwtAuthenticationFilter();
//  }

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    // Get AuthenticationManager Bean
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(service).passwordEncoder(passwordEncoder());
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
//                .httpBasic().and()
        .authorizeRequests()
        .antMatchers("/user").hasRole("ADMIN")// Cho phép  truy cập vào  địa chỉ này
        .antMatchers("/v3/api-docs/**",
            "/swagger-ui/**", "/swagger-ui.html/**").permitAll()
        .antMatchers("/user", "/logina").permitAll()
//            .antMatchers("/free").hasAnyAuthority("ADMIN", "USER", "AUTHOR", "EDITOR")
//            .antMatchers("/author").hasAnyAuthority("AUTHOR")
//            .antMatchers("/user").hasAnyAuthority("USER")
//            .antMatchers("/editor").hasAnyAuthority("EDITOR")
        .anyRequest().authenticated().and();

//        .and().formLogin()
//        .permitAll();// Tất cả các request khác đều cần phải xác thực mới

//    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

  }

//
}
