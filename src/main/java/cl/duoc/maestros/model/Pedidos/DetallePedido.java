package cl.duoc.maestros.model.Pedidos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Entity 
@Table(name = "DETALLE_PEDIDO")
@IdClass(DetallePedidoId.class)
public class DetallePedido {
    @Id 
    @Column(name = "ID_PEDIDO")
    private Long idPedido;

    @Id 
    @Column(name = "ID_PRODUCTO")
    private Long idProducto;

    @Column(name = "CANTIDAD")
    private int cantidad;

    @Column(name = "PRECIO_UNITARIO")
    private int precioUnitario;
}
