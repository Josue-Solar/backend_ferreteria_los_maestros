package cl.duoc.maestros.model.Pedidos;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoId implements Serializable {
    private Long idPedido;
    private Long idProducto;
}