package cl.duoc.maestros.service.Auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import cl.duoc.maestros.dto.LoginRequest;
import cl.duoc.maestros.dto.LoginResponse;
import cl.duoc.maestros.dto.RegistroRequest;
import cl.duoc.maestros.dto.UsuarioRequest;
import cl.duoc.maestros.model.Usuarios.Cliente.Cliente;
import cl.duoc.maestros.model.Usuarios.Usuario;
import cl.duoc.maestros.repository.Usuarios.Admin.AdminRespository;
import cl.duoc.maestros.repository.Usuarios.Cliente.ClienteRepository;
import cl.duoc.maestros.repository.Usuarios.Cliente.TipoClienteRepository;
import cl.duoc.maestros.repository.Usuarios.UsuarioRepository;
import cl.duoc.maestros.security.JwtService;
import cl.duoc.maestros.service.Usuarios.UsuarioService;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final AdminRespository adminRepository;
    private final ClienteRepository clienteRepository;
    private final TipoClienteRepository tipoClienteRepository;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       AdminRespository adminRepository,
                       ClienteRepository clienteRepository,
                       TipoClienteRepository tipoClienteRepository,
                       UsuarioService usuarioService,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.adminRepository = adminRepository;
        this.clienteRepository = clienteRepository;
        this.tipoClienteRepository = tipoClienteRepository;
        this.usuarioService = usuarioService;
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

    // Registro publico: crea el usuario y su ficha de cliente (mismo id) y deja la sesion iniciada.
    // Lanza IllegalArgumentException si el correo/usuario ya existen o el tipo de cliente no existe.
    @Transactional
    public LoginResponse registrar(RegistroRequest r) {
        if (!tipoClienteRepository.existsById(r.idTipoCliente())) {
            throw new IllegalArgumentException("El tipo de cliente no existe");
        }
        Usuario usuario = usuarioService.crear(new UsuarioRequest(
                r.nombreUsuario(), r.rut(), r.primerNombre(), r.segundoNombre(),
                r.apePaterno(), r.apeMaterno(), r.telefono(), r.correo(),
                r.contrasenia(), r.fechaNacimiento()));
        clienteRepository.save(new Cliente(usuario.getId(), r.idTipoCliente(), r.region(), r.comuna()));
        return construirRespuesta(usuario);
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
