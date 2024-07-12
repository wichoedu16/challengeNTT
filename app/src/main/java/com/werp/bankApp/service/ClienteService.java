package com.werp.bankApp.service;

import com.werp.bankApp.entity.Cliente;
import com.werp.bankApp.entity.Persona;
import com.werp.bankApp.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    PersonaService personaService;

    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    public Cliente getById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            return cliente.get();
        } else {
            throw new EntityNotFoundException("No se encontró el cliente con ID " + id);
        }
    }

    public Cliente create(Cliente cliente) {
        cliente.setPersonaId(cliente.getClienteId());
        return clienteRepository.save(cliente);
    }

    public Cliente update(Long id, Cliente cliente) {
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(id);
        if (clienteEncontrado.isPresent()) {
            cliente.setClienteId(id);
            return clienteRepository.save(cliente);
        }else {
            throw new EntityNotFoundException("No se encontró el cliente con ID " + id);
        }
    }

    public void delete(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            clienteRepository.delete(cliente.get());
        }else {
            throw new EntityNotFoundException("No se encontró el cliente con ID " + id);
        }
    }

}
