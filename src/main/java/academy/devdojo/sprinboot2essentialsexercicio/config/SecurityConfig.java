package academy.devdojo.sprinboot2essentialsexercicio.config;

import academy.devdojo.sprinboot2essentialsexercicio.service.DevDojoUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@SuppressWarnings("java:S5344")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DevDojoUserDetailsService devDojoUserDetailsService;
    /**
     * BasicAuthenticationFilter - verifica se voce tem o token  - usuario::senha codificado em base64
     * UsernamePasswordAuthenticationFilter - verifica se na requisicao tem usuario e senha
     * DefaultLoginPageGeneratingFilter
     * DefaultLogoutPageGeneratingFilter
     * FilterSecurityInterceptor - Verifica se voce esta autorizado
     * Authentication (Passar Dados para verificar) -> Authorization (Se dados Ok, recebe algumas autorizacoes)
     * @param http
     * @throws Exception
     */
    @Override //Oque vc quer proteger //Nesse caso todas requisicoes
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
//        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .authorizeRequests()
                .antMatchers("/animes/admin/**").hasRole("ADMIN") // + restritivo primeiro
                .antMatchers("/animes/**").hasRole("USER")
                .antMatchers("/actuator/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Override //Criar usuario em memoria
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password encoded {}", passwordEncoder.encode("academy"));

        auth.inMemoryAuthentication()
                .withUser("willian2")
                .password(passwordEncoder.encode("academy"))
                .roles("USER","ADMIN")
                .and()
                .withUser("devdojo2")
                .password(passwordEncoder.encode("academy"))
                .roles("USER");

        auth.userDetailsService(devDojoUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
