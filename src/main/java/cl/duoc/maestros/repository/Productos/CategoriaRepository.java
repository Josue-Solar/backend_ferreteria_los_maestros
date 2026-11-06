package cl.duoc.maestros.repository.Productos;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.maestros.model.Productos.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    
}
