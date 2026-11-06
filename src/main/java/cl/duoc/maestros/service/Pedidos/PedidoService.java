package cl.duoc.maestros.service.Pedidos;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.maestros.model.Pedidos.Pedido;
import cl.duoc.maestros.repository.Pedidos.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
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