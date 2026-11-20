package cl.duoc.maestros.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtService jwtService) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // API sin sesion ni cookies: CSRF no aplica
            .cors(Customizer.withDefaults()) // respeta los @CrossOrigin de los controllers
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/error").permitAll()
                // publico: login y documentacion Swagger
                .requestMatchers("/api/auth/login", "/api/auth/registro", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                // catalogo de productos visible sin iniciar sesion
                .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()
                // modo invitado: catalogo de solo lectura sin iniciar sesion
                .requestMatchers(HttpMethod.GET, "/api/invitado/**").permitAll()
                // crear, editar y eliminar productos: solo ADMIN
                .requestMatchers("/api/productos/**").hasRole("ADMIN")
                // gestion de clientes: solo ADMIN
                .requestMatchers("/api/clientes/**").hasRole("ADMIN")
                // gestion de usuarios: solo ADMIN
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                // los pedidos y productos comprados del propio cliente
                .requestMatchers("/api/pedidos/mis-pedidos").hasRole("CLIENTE")
                // pedidos: cualquier usuario autenticado
                .requestMatchers("/api/pedidos/**").authenticated()
                .anyRequest().authenticated())
            .addFilterBefore(new JwtAuthFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
