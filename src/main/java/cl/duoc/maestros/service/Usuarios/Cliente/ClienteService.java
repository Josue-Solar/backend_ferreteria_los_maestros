package cl.duoc.maestros.service.Usuarios.Cliente;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.maestros.model.Usuarios.Cliente.Cliente;
import cl.duoc.maestros.repository.Usuarios.Cliente.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // obtener todos los registros
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    // buscar por id
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    // guardar y crear nuevo registro
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // actualizar registro existente
    public Optional<Cliente> actualizar(Long id, Cliente datos) {
        return clienteRepository.findById(id).map(existente -> {
            existente.setIdTipoCliente(datos.getIdTipoCliente());
            existente.setRegion(datos.getRegion());
            existente.setComuna(datos.getComuna());
            return clienteRepository.save(existente);
        });
    }

    // eliminar por id
    public boolean eliminar(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}