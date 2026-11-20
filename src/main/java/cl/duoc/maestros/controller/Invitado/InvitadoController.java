package cl.duoc.maestros.controller.Invitado;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.maestros.model.Productos.Categoria;
import cl.duoc.maestros.model.Productos.Producto;
import cl.duoc.maestros.service.Productos.CategoriaService;
import cl.duoc.maestros.service.Productos.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

// Modo invitado: solo lectura y sin token. No filtra nada: el invitado ve
// el mismo catalogo completo que un usuario con sesion (incluidos productos sin stock).
@RestController
@RequestMapping("/api/invitado")
@CrossOrigin(origins = "http://localhost:5173") // front React (Vite)
@Tag(name = "Invitado", description = "Catálogo de solo lectura para visitantes sin sesión")
public class InvitadoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;

    public InvitadoController(ProductoService productoService, CategoriaService categoriaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "Ver todos los productos (sin ocultar ninguno)")
    @GetMapping("/productos")
    public List<Producto> productos() {
        return productoService.obtenerTodos();
    }

    @Operation(summary = "Ver el detalle de un producto")
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> producto(@PathVariable Long id) {
        return productoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Ver las categorías")
    @GetMapping("/categorias")
    public List<Categoria> categorias() {
        return categoriaService.obtenerTodas();
    }
}
