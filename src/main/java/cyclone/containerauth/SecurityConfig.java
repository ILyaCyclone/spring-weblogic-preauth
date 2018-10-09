package cyclone.containerauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig
        extends WebSecurityConfigurerAdapter {


//    @Bean
//    public AuthenticationUserDetailsService jeeUserDetailsService() {
//        PreAuthenticatedGrantedAuthoritiesUserDetailsService userDetailsService = new PreAuthenticatedGrantedAuthoritiesUserDetailsService();
////        userDetailsService.
//        return userDetailsService;
//    }

        @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                  .authorizeRequests()
                    .antMatchers("/login", "/login*", "/public", "/resources/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .jee()
//                    .authenticatedUserDetailsService(jeeUserDetailsService())
                    .mappableRoles("personal_office", "PERSONAL_OFFICE"
                            , "ROLE_personal_office", "ROLE_PERSONAL_OFFICE");

        }

}
