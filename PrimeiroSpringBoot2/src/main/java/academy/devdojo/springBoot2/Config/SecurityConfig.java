package academy.devdojo.springBoot2.Config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true) // Habilita segurança em métodos
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/animes/admin/**").hasRole("ADMIN")// Somente ADMIN
                        .requestMatchers("/animes/**").hasRole("USER") // Somente USER
                        .anyRequest().authenticated() // Outras rotas autenticadas
                )
                .httpBasic(Customizer.withDefaults()) // Configura autenticação básica
                .formLogin(Customizer.withDefaults()) // Configura login de formulário
                .csrf(csrf -> csrf.disable()); // Desabilita CSRF (Cross-Site Request Forgery)

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() { // Cria um codificador de senha
        return new BCryptPasswordEncoder(); // BCrypt forte e seguro
    }
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        String encodedPassword = encoder.encode("pierani"); // Senha criptografada
        log.info("Senha criptografada: {}", encodedPassword); // Loga a senha criptografada

        UserDetails user = User.builder() // Cria um usuário
                .username("Pedro") // Nome de usuário
                .password(encodedPassword) // Senha criptografada
                .roles("ADMIN", "USER") // Papel do usuário
                .build(); // Cria detalhes do usuário

        return new InMemoryUserDetailsManager(user); // Armazena o usuário em memória
    }
}


