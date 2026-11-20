package cl.duoc.maestros.service.Pedidos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.maestros.model.Pedidos.Pedido;
import cl.duoc.maestros.repository.Pedidos.PedidoRepository;
import cl.duoc.maestros.util.TestDataFactory;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void obtenerTodos_devuelveLaListaDelRepositorio() {
        List<Pedido> pedidos = List.of(TestDataFactory.pedido(), TestDataFactory.pedido());
        when(pedidoRepository.findAll()).thenReturn(pedidos);

        List<Pedido> resultado = pedidoService.obtenerTodos();

        assertEquals(2, resultado.size());
    }

    @Test
    void buscarPorId_existente_devuelveElPedido() {
        Pedido pedido = TestDataFactory.pedido();
        pedido.setId(1L);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Optional<Pedido> resultado = pedidoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(pedido.getTotal(), resultado.get().getTotal());
    }

    @Test
    void buscarPorId_inexistente_devuelveVacio() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Pedido> resultado = pedidoService.buscarPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void guardar_conFecha_laConserva() {
        Pedido nuevo = TestDataFactory.pedido();
        Date fecha = Date.valueOf("2026-01-15");
        nuevo.setFecha(fecha);
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        Pedido resultado = pedidoService.guardar(nuevo);

        assertEquals(fecha, resultado.getFecha());
    }

    @Test
    void guardar_sinFecha_asignaLaFechaDeHoy() {
        Pedido nuevo = TestDataFactory.pedido();
        nuevo.setFecha(null);
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        Pedido resultado = pedidoService.guardar(nuevo);

        assertNotNull(resultado.getFecha());
    }

    @Test
    void actualizar_existente_cambiaLosCampos() {
        Pedido existente = TestDataFactory.pedido();
        existente.setId(1L);
        Pedido datos = TestDataFactory.pedido();

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Pedido> resultado = pedidoService.actualizar(1L, datos);

        assertTrue(resultado.isPresent());
        assertEquals(datos.getIdCliente(), resultado.get().getIdCliente());
        assertEquals(datos.getTotal(), resultado.get().getTotal());
        assertEquals(1L, resultado.get().getId()); // el id no cambia
    }

    @Test
    void actualizar_sinFecha_mantieneLaFechaOriginal() {
        Pedido existente = TestDataFactory.pedido();
        existente.setId(1L);
        Date fechaOriginal = Date.valueOf("2026-01-15");
        existente.setFecha(fechaOriginal);

        Pedido datos = TestDataFactory.pedido();
        datos.setFecha(null);

        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Pedido> resultado = pedidoService.actualizar(1L, datos);

        assertTrue(resultado.isPresent());
        assertEquals(fechaOriginal, resultado.get().getFecha());
    }

    @Test
    void actualizar_inexistente_devuelveVacioYNoGuarda() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Pedido> resultado = pedidoService.actualizar(99L, TestDataFactory.pedido());

        assertFalse(resultado.isPresent());
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void eliminar_existente_devuelveTrueYElimina() {
        when(pedidoRepository.existsById(1L)).thenReturn(true);

        boolean resultado = pedidoService.eliminar(1L);

        assertTrue(resultado);
        verify(pedidoRepository).deleteById(1L);
    }

    @Test
    void eliminar_inexistente_devuelveFalseYNoElimina() {
        when(pedidoRepository.existsById(99L)).thenReturn(false);

        boolean resultado = pedidoService.eliminar(99L);

        assertFalse(resultado);
        verify(pedidoRepository, never()).deleteById(99L);
    }
}