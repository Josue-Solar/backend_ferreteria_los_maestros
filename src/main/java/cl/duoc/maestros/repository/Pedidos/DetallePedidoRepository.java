package cl.duoc.maestros.repository.Pedidos;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.maestros.model.Pedidos.DetallePedido;
import cl.duoc.maestros.model.Pedidos.DetallePedidoId;

// DetallePedido tiene clave compuesta (idPedido + idProducto), por eso el ID es DetallePedidoId
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, DetallePedidoId> {

}