package website.psuti.fist.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import website.psuti.fist.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity config) throws Exception {
        config.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin").permitAll()
                .antMatchers("/admin/**").authenticated()//.permitAll()
                /*.antMatchers("/warspear/buyAccountYxi").permitAll()
                .antMatchers("/warspear/buyAccountYxi/**").permitAll()
                .antMatchers("/warspear/buyAccountGory").permitAll()
                .antMatchers("/warspear/buyAccountGory/**").permitAll()
                .antMatchers("/warspear/payment").permitAll()
                .antMatchers("/warspear/notification").permitAll()
                .antMatchers("/warspear/registration").permitAll()
                .antMatchers("/warspear/registration/**").permitAll()
                .antMatchers("/warspear/**").authenticated()*/
                .and()
                .formLogin().loginPage("/admin/login").defaultSuccessUrl("/admin").permitAll()
                .and()
                .logout().logoutUrl("/admin/logout").permitAll().invalidateHttpSession(true).permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(bcryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
