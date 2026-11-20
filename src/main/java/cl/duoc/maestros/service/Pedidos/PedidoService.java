package cl.duoc.maestros.service.Pedidos;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cl.duoc.maestros.dto.PedidoDetalleResponse;
import cl.duoc.maestros.model.Pedidos.DetallePedido;
import cl.duoc.maestros.model.Pedidos.Pedido;
import cl.duoc.maestros.model.Productos.Producto;
import cl.duoc.maestros.repository.Pedidos.DetallePedidoRepository;
import cl.duoc.maestros.repository.Pedidos.PedidoRepository;
import cl.duoc.maestros.repository.Productos.ProductoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detalleRepository;
    private final ProductoRepository productoRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         DetallePedidoRepository detalleRepository,
                         ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.detalleRepository = detalleRepository;
        this.productoRepository = productoRepository;
    }

    // pedidos de un cliente (el id del cliente es el mismo id del usuario), con los productos de cada uno
    public List<PedidoDetalleResponse> obtenerPorCliente(Long idCliente) {
        return pedidoRepository.findByIdClienteOrderByIdDesc(idCliente).stream()
                .map(pedido -> {
                    List<DetallePedido> detalles = detalleRepository.findByIdPedido(pedido.getId());
                    Map<Long, Producto> productos = productoRepository
                            .findAllById(detalles.stream().map(DetallePedido::getIdProducto).toList())
                            .stream()
                            .collect(Collectors.toMap(Producto::getId, Function.identity()));
                    List<PedidoDetalleResponse.Item> items = detalles.stream().map(d -> {
                        Producto prod = productos.get(d.getIdProducto());
                        return new PedidoDetalleResponse.Item(
                                d.getIdProducto(),
                                prod != null ? prod.getNombre() : null,
                                prod != null ? prod.getUrlImagen() : null,
                                d.getCantidad(),
                                d.getPrecioUnitario(),
                                d.getCantidad() * d.getPrecioUnitario());
                    }).toList();
                    return new PedidoDetalleResponse(pedido.getId(), pedido.getFecha(), pedido.getTotal(), items);
                }).toList();
    }

    // obtener todos los registros
    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    // buscar por id
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    // guardar o crear nuevo registro
    // Si no viene fecha, se pone la de hoy (la columna FECHA no acepta NULL)
    public Pedido guardar(Pedido pedido) {
        if (pedido.getFecha() == null) {
            pedido.setFecha(new Date(System.currentTimeMillis()));
        }
        return pedidoRepository.save(pedido);
    }

    // actualizar registro que ya existe
    public Optional<Pedido> actualizar(Long id, Pedido datos) {
        return pedidoRepository.findById(id).map(existente -> {
            existente.setIdCliente(datos.getIdCliente());
            existente.setTotal(datos.getTotal());
            if (datos.getFecha() != null) {
                existente.setFecha(datos.getFecha());
            }
            return pedidoRepository.save(existente);
        });
    }

    // eliminar por id
    public boolean eliminar(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}