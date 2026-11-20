package cl.duoc.maestros.dto;

import java.sql.Date;
import java.util.List;

// Pedido de un cliente con los productos que compro
public record PedidoDetalleResponse(
        Long id,
        Date fecha,
        Long total,
        List<Item> productos) {

    public record Item(
            Long idProducto,
            String nombre,
            String urlImagen,
            int cantidad,
            int precioUnitario,
            int subtotal) {
    }
}
