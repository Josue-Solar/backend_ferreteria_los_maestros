package cl.duoc.maestros.repository.Usuarios.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.maestros.model.Usuarios.Cliente.TipoCliente;

public interface TipoClienteRepository extends JpaRepository<TipoCliente, Long>{
    
}
