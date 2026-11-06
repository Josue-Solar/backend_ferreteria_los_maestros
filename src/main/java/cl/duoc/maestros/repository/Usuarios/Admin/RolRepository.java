package cl.duoc.maestros.repository.Usuarios.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.maestros.model.Usuarios.Admin.Rol;

public interface RolRepository extends JpaRepository<Rol, Long>{
    
}
