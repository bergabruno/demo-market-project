package br.com.mercado.configurations.security;

import br.com.mercado.security.AdminDetailsServiceImpl;
import br.com.mercado.security.JWTAutheticationFilter;
import br.com.mercado.security.JWTAuthorizationFilter;
import br.com.mercado.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

@Configuration
@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AdminDetailsServiceImpl adminDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    private static final String [] PUBLIC_MATCHERS_GET = {
            "/api/v1/produtos/**",
            "/api/v1/categorias/**",
            "/api/v1/estoque/**"
    };

    private static final String [] PUBLIC_MATCHERS_POST = {
            "/api/v1/pedidos/**",
            "/api/v1/clientes/**",
            "/api/v1/admin/login/**"
    };

    private static final String [] PUBLIC_MATCHERS_PUT = {
            "/api/v1/pedidos/alterar-pagamento/**",
            "/api/v1/pedidos/finalizar/**"
    };

    private static final String [] PUBLIC_MATCHERS_SWAGGER = {
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-ui/**",
            "/swagger-ui/index.html#",
            "/v3/api-docs",
            "/v2/api-docs"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
                .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
                .antMatchers(HttpMethod.PUT, PUBLIC_MATCHERS_PUT).permitAll()
                .antMatchers(PUBLIC_MATCHERS_SWAGGER).permitAll()
//                .antMatchers("/**").permitAll()
                //para rodar o teste do controller, liberar aqui
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors()
                .and().csrf().disable();
        http.addFilter(new JWTAutheticationFilter(authenticationManager(), jwtUtil));
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, adminDetailsService));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource sourse = new UrlBasedCorsConfigurationSource();
        sourse.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return sourse;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
