package cl.duoc.maestros.service.Usuarios.Cliente;

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

import cl.duoc.maestros.model.Usuarios.Cliente.Cliente;
import cl.duoc.maestros.repository.Usuarios.Cliente.ClienteRepository;
import cl.duoc.maestros.util.TestDataFactory;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void obtenerTodos_devuelveLaListaDelRepositorio() {
        List<Cliente> clientes = List.of(TestDataFactory.cliente(), TestDataFactory.cliente());
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> resultado = clienteService.obtenerTodos();

        assertEquals(2, resultado.size());
    }

    @Test
    void buscarPorId_existente_devuelveElCliente() {
        Cliente cliente = TestDataFactory.cliente();
        cliente.setId(5L);
        when(clienteRepository.findById(5L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = clienteService.buscarPorId(5L);

        assertTrue(resultado.isPresent());
        assertEquals(cliente.getComuna(), resultado.get().getComuna());
    }

    @Test
    void buscarPorId_inexistente_devuelveVacio() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Cliente> resultado = clienteService.buscarPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void guardar_devuelveElClienteGuardado() {
        Cliente nuevo = TestDataFactory.cliente();
        when(clienteRepository.save(nuevo)).thenReturn(nuevo);

        Cliente resultado = clienteService.guardar(nuevo);

        assertNotNull(resultado);
        assertEquals(nuevo.getRegion(), resultado.getRegion());
        verify(clienteRepository).save(nuevo);
    }

    @Test
    void actualizar_existente_cambiaLosCampos() {
        Cliente existente = TestDataFactory.cliente();
        existente.setId(5L);
        Cliente datos = TestDataFactory.cliente();

        when(clienteRepository.findById(5L)).thenReturn(Optional.of(existente));
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Cliente> resultado = clienteService.actualizar(5L, datos);

        assertTrue(resultado.isPresent());
        assertEquals(datos.getRegion(), resultado.get().getRegion());
        assertEquals(datos.getComuna(), resultado.get().getComuna());
        assertEquals(datos.getIdTipoCliente(), resultado.get().getIdTipoCliente());
        assertEquals(5L, resultado.get().getId()); // el id no cambia
    }

    @Test
    void actualizar_inexistente_devuelveVacioYNoGuarda() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Cliente> resultado = clienteService.actualizar(99L, TestDataFactory.cliente());

        assertFalse(resultado.isPresent());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void eliminar_existente_devuelveTrueYElimina() {
        when(clienteRepository.existsById(5L)).thenReturn(true);

        boolean resultado = clienteService.eliminar(5L);

        assertTrue(resultado);
        verify(clienteRepository).deleteById(5L);
    }

    @Test
    void eliminar_inexistente_devuelveFalseYNoElimina() {
        when(clienteRepository.existsById(99L)).thenReturn(false);

        boolean resultado = clienteService.eliminar(99L);

        assertFalse(resultado);
        verify(clienteRepository, never()).deleteById(99L);
    }
}