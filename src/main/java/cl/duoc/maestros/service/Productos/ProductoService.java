package cl.duoc.maestros.service.Productos;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.maestros.model.Productos.Producto;
import cl.duoc.maestros.repository.Productos.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // todos los registros
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    // buscar por id
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Guardar/crear nuevo registro
    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    // actualizar registro existente
    // Devuelve un optional vacio siesk el id no existe
    public Optional<Producto> actualizar(Long id, Producto datos) {
        return productoRepository.findById(id).map(existente -> {
            existente.setUrlImagen(datos.getUrlImagen());
            existente.setNombre(datos.getNombre());
            existente.setDescripcion(datos.getDescripcion());
            existente.setDetalle(datos.getDetalle());
            existente.setPrecio(datos.getPrecio());
            existente.setId_CATEGORIA(datos.getId_CATEGORIA());
            existente.setSku(datos.getSku());
            existente.setMarca(datos.getMarca());
            existente.setUnidad(datos.getUnidad());
            existente.setStock(datos.getStock());
            return productoRepository.save(existente);
        });
    }

    // eliminar por id. (true si existe y lo elimino, false siesk no existia:p)
    public boolean eliminar(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}