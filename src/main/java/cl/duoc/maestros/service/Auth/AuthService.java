package cl.duoc.maestros.service.Auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.duoc.maestros.dto.LoginRequest;
import cl.duoc.maestros.dto.LoginResponse;
import cl.duoc.maestros.model.Usuarios.Usuario;
import cl.duoc.maestros.repository.Usuarios.Admin.AdminRespository;
import cl.duoc.maestros.repository.Usuarios.Cliente.ClienteRepository;
import cl.duoc.maestros.repository.Usuarios.UsuarioRepository;
import cl.duoc.maestros.security.JwtService;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final AdminRespository adminRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       AdminRespository adminRepository,
                       ClienteRepository clienteRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.adminRepository = adminRepository;
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Devuelve el token si las credenciales son correctas; Optional vacio si no.
    // No distingue "usuario no existe" de "clave incorrecta" a proposito.
    public Optional<LoginResponse> login(LoginRequest request) {
        return usuarioRepository.findByCorreo(request.usuario())
                .or(() -> usuarioRepository.findByNombreUsuario(request.usuario()))
                .filter(u -> contraseniaValida(request.contrasenia(), u.getContrasenia()))
                .map(this::construirRespuesta);
    }

    private LoginResponse construirRespuesta(Usuario usuario) {
        String rol = obtenerRol(usuario.getId());
        String token = jwtService.generarToken(usuario, rol);
        return new LoginResponse(token, "Bearer", jwtService.getExpirationMs(),
                usuario.getId(), usuario.getNombreUsuario(), rol);
    }

    // Admin y Cliente comparten el id del Usuario
    private String obtenerRol(Long idUsuario) {
        if (adminRepository.existsById(idUsuario)) {
            return "ADMIN";
        }
        if (clienteRepository.existsById(idUsuario)) {
            return "CLIENTE";
        }
        return "USUARIO";
    }

    // Acepta claves con hash BCrypt y, para datos de prueba ya existentes, claves en texto plano
    private boolean contraseniaValida(String ingresada, String guardada) {
        if (guardada == null) {
            return false;
        }
        if (guardada.startsWith("$2a$") || guardada.startsWith("$2b$") || guardada.startsWith("$2y$")) {
            return passwordEncoder.matches(ingresada, guardada);
        }
        return MessageDigest.isEqual(
                ingresada.getBytes(StandardCharsets.UTF_8),
                guardada.getBytes(StandardCharsets.UTF_8));
    }
}
