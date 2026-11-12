package cl.duoc.maestros.repository.Usuarios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.maestros.model.Usuarios.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
