package cl.duoc.maestros.service.Usuarios;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.duoc.maestros.dto.UsuarioRequest;
import cl.duoc.maestros.model.Usuarios.Usuario;
import cl.duoc.maestros.repository.Usuarios.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // obtener todos los registros
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // buscar por id
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // crear nuevo usuario; lanza IllegalArgumentException si falta la clave o el correo/usuario ya existen
    public Usuario crear(UsuarioRequest datos) {
        if (datos.contrasenia() == null || datos.contrasenia().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        validarUnicos(datos, null);

        Usuario usuario = new Usuario();
        copiarDatos(usuario, datos);
        usuario.setContrasenia(passwordEncoder.encode(datos.contrasenia()));
        return usuarioRepository.save(usuario);
    }

    // actualizar usuario existente; Optional vacio si el id no existe.
    // Si no se envia contrasenia se conserva la actual.
    public Optional<Usuario> actualizar(Long id, UsuarioRequest datos) {
        return usuarioRepository.findById(id).map(existente -> {
            validarUnicos(datos, id);
            copiarDatos(existente, datos);
            if (datos.contrasenia() != null && !datos.contrasenia().isBlank()) {
                existente.setContrasenia(passwordEncoder.encode(datos.contrasenia()));
            }
            return usuarioRepository.save(existente);
        });
    }

    // eliminar por id (true si existia). Admin y cliente se borran en cascada por la FK
    public boolean eliminar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void copiarDatos(Usuario usuario, UsuarioRequest datos) {
        usuario.setNombreUsuario(datos.nombreUsuario());
        usuario.setRut(datos.rut());
        usuario.setPrimerNombre(datos.primerNombre());
        usuario.setSegundoNombre(datos.segundoNombre());
        usuario.setApePaterno(datos.apePaterno());
        usuario.setApeMaterno(datos.apeMaterno());
        usuario.setTelefono(datos.telefono());
        usuario.setCorreo(datos.correo());
        usuario.setFechaNacimiento(datos.fechaNacimiento());
    }

    // correo y nombre_usuario son UNIQUE en la BD; se avisa antes de que falle el INSERT/UPDATE
    private void validarUnicos(UsuarioRequest datos, Long idActual) {
        if (datos.correo() != null && !datos.correo().isBlank()) {
            usuarioRepository.findByCorreo(datos.correo())
                    .filter(u -> !u.getId().equals(idActual))
                    .ifPresent(u -> { throw new IllegalArgumentException("El correo ya está registrado"); });
        }
        if (datos.nombreUsuario() != null && !datos.nombreUsuario().isBlank()) {
            usuarioRepository.findByNombreUsuario(datos.nombreUsuario())
                    .filter(u -> !u.getId().equals(idActual))
                    .ifPresent(u -> { throw new IllegalArgumentException("El nombre de usuario ya existe"); });
        }
    }
}
