package cl.duoc.maestros.service.Productos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.maestros.model.Productos.Producto;
import cl.duoc.maestros.repository.Productos.ProductoRepository;
import cl.duoc.maestros.util.TestDataFactory;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void obtenerTodos_devuelveLaListaDelRepositorio() {
        List<Producto> productos = List.of(TestDataFactory.producto(), TestDataFactory.producto());
        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> resultado = productoService.obtenerTodos();

        assertEquals(2, resultado.size());
    }

    @Test
    void buscarPorId_existente_devuelveElProducto() {
        Producto producto = TestDataFactory.producto();
        producto.setId(1L);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> resultado = productoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(producto.getNombre(), resultado.get().getNombre());
    }

    @Test
    void buscarPorId_inexistente_devuelveVacio() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Producto> resultado = productoService.buscarPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void guardar_devuelveElProductoGuardado() {
        Producto nuevo = TestDataFactory.producto();
        when(productoRepository.save(nuevo)).thenReturn(nuevo);

        Producto resultado = productoService.guardar(nuevo);

        assertNotNull(resultado);
        assertEquals(nuevo.getSku(), resultado.getSku());
        verify(productoRepository).save(nuevo);
    }

    @Test
    void actualizar_existente_cambiaLosCampos() {
        Producto existente = TestDataFactory.producto();
        existente.setId(1L);
        Producto datos = TestDataFactory.producto();

        when(productoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(productoRepository.save(any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Producto> resultado = productoService.actualizar(1L, datos);

        assertTrue(resultado.isPresent());
        assertEquals(datos.getNombre(), resultado.get().getNombre());
        assertEquals(datos.getPrecio(), resultado.get().getPrecio());
        assertEquals(datos.getStock(), resultado.get().getStock());
        assertEquals(1L, resultado.get().getId()); // el id no cambia
    }

    @Test
    void actualizar_inexistente_devuelveVacioYNoGuarda() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Producto> resultado = productoService.actualizar(99L, TestDataFactory.producto());

        assertFalse(resultado.isPresent());
        verify(productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void eliminar_existente_devuelveTrueYElimina() {
        when(productoRepository.existsById(1L)).thenReturn(true);

        boolean resultado = productoService.eliminar(1L);

        assertTrue(resultado);
        verify(productoRepository).deleteById(1L);
    }

    @Test
    void eliminar_inexistente_devuelveFalseYNoElimina() {
        when(productoRepository.existsById(99L)).thenReturn(false);

        boolean resultado = productoService.eliminar(99L);

        assertFalse(resultado);
        verify(productoRepository, never()).deleteById(99L);
    }
}