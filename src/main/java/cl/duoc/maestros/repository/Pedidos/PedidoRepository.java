package cl.duoc.maestros.repository.Pedidos;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.maestros.model.Pedidos.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    
}
