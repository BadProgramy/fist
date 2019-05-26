package website.psuti.fist.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import website.psuti.fist.model.Role;
import website.psuti.fist.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity config) throws Exception {
        config.csrf().disable().headers().cacheControl().disable().disable()
                .authorizeRequests()
                .antMatchers("/admin").hasAnyAuthority(Role.DEVELOPER.getAuthority(), Role.MODERATOR.getAuthority(), Role.ADMIN.getAuthority())
                .antMatchers("/admin/content/**").hasAuthority(Role.MODERATOR.getAuthority())
                .antMatchers("/admin/page/**").hasAuthority(Role.ADMIN.getAuthority())
                .antMatchers("/admin/table/**").hasAuthority(Role.DEVELOPER.getAuthority())
                .antMatchers("/admin/user/**").hasAuthority(Role.ADMIN.getAuthority())
                .antMatchers("/admin/setting/**").hasAnyAuthority(Role.DEVELOPER.getAuthority(), Role.MODERATOR.getAuthority(), Role.ADMIN.getAuthority())

                .and()
                .formLogin().loginPage("/admin/login").defaultSuccessUrl("/admin").permitAll()
                .and()
                .logout().logoutUrl("/admin/logout").permitAll().invalidateHttpSession(true).permitAll();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
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
