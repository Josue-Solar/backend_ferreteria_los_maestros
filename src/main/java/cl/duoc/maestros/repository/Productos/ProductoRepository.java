package cl.duoc.maestros.repository.Productos;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.maestros.model.Productos.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
}
