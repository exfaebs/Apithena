package net.ictcampus.apithena.controller.configurations;

import net.ictcampus.apithena.controller.security.JWTAuthenticationFilter;
import net.ictcampus.apithena.controller.security.JWTAuthorizationFilter;
import net.ictcampus.apithena.controller.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static net.ictcampus.apithena.controller.security.SecurityConstants.API_DOCUMENTATION_URLS;
import static net.ictcampus.apithena.controller.security.SecurityConstants.SIGN_UP_URL;
import static org.apache.commons.lang3.BooleanUtils.and;

@Configuration //indicates, this is a configuration class
@EnableWebSecurity //indicates, that Setting for Security ar in this class

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfiguration(UserDetailsServiceImpl userDetailsService,
                                 BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     *
     * @param http Http-Entity of spring framework
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                //Excepts Sign-up and Swagger-URLs from Authentication
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll() //only Post-Requests are allowed
                .antMatchers(HttpMethod.GET, API_DOCUMENTATION_URLS).permitAll() //only Get-Requests are Allowed
                .anyRequest().authenticated() //all (other) requests need to be authenticated
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager())) //Our Method will be used throughout project
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))  //Our Method will be used throughout project
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * Will be used for a Frontend to access and connect
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}
