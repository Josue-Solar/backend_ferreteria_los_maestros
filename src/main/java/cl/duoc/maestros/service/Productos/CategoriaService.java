package cl.duoc.maestros.service.Productos;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.maestros.model.Productos.Categoria;
import cl.duoc.maestros.repository.Productos.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }
}
