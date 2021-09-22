package mozzi.springproject.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/home").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
      throws Exception {
        auth.jdbcAuthentication()
          .dataSource(dataSource)
          .passwordEncoder(passwordEncoder())
          .usersByUsernameQuery("select username,password,enabled "
            + "from user "
            + "where username = ?") // Authentication
          .authoritiesByUsernameQuery("select username, name "
            + "from authorities "
            + "where email = ?"); // Authorization
    }
    // Authentication : 로그인
    // Authorization : 권한

    // @OneToOne  ex. user-user_detail
    // @OneToMany  ex. user - board
    // @ManyToOne  ex. board - user
    // @ManyToMany  ex. user - role

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}