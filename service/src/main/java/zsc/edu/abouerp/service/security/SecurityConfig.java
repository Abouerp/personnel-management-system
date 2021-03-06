package zsc.edu.abouerp.service.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import zsc.edu.abouerp.service.security.handler.LogoutHandler;
import zsc.edu.abouerp.service.security.imagecode.ValidateCodeFilter;

/**
 * @author Abouerp
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final LogoutHandler logoutHandler;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate stringRedisTemplate;

    public SecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler,
                          AuthenticationFailureHandler authenticationFailureHandler,
                          LogoutHandler logoutHandler,
                          @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder,
                          StringRedisTemplate stringRedisTemplate) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.logoutHandler = logoutHandler;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter(authenticationFailureHandler,stringRedisTemplate);
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/api/user/login")
                .permitAll()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)

                .and()

                .logout()
                .logoutUrl("/api/user/logout")
                .permitAll()
                .logoutSuccessHandler(logoutHandler)

                .and()

                .authorizeRequests()
                .antMatchers("/api/user/me").permitAll()
                .antMatchers(HttpMethod.GET, "/api/storage/preview/**").permitAll()
                .antMatchers("/api/**").authenticated()

                .and()

                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }
}
