package cl.duoc.maestros.repository.Usuarios;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.maestros.model.Usuarios.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
