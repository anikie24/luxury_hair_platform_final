package za.ac.cput.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import za.ac.cput.services.UserLoginService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final UserLoginService userLoginService;

    @Autowired
    public SecurityConfig(@Lazy JwtRequestFilter jwtRequestFilter, UserLoginService userLoginService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.userLoginService = userLoginService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/userlogin/create", "/userlogin/read", "/public/**").permitAll()
                                .requestMatchers("/userlogin/admin/**").hasAuthority("Admin")
                                .requestMatchers("/userlogin/customer/**").hasAuthority("Customer")
                                .requestMatchers("/Products/**").hasRole("Customer")
                                .requestMatchers("/AdminProducts/**").hasRole("Admin")
                                .requestMatchers("/product/**").permitAll()
                                .requestMatchers("/cart/**").permitAll()
                                .requestMatchers("/userlogin/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).formLogin(form -> form
                        .loginPage("/read")
                        .loginProcessingUrl("/userlogin/authenticate")

                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userLoginService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
