package com.werp.bankApp.service;

import com.werp.bankApp.entity.Cliente;
import com.werp.bankApp.repository.ClienteRepository;
import com.werp.bankApp.response.ClienteResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    @Autowired

    public ResponseEntity<ClienteResponseRest> getAll() {
        ClienteResponseRest response = new ClienteResponseRest();
        try {
            List<Cliente> clientes = clienteRepository.findAll();
            response.getClienteResponse().setClientes(clientes);
            response.setMetadata("Ok", "00", "Éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al consultar");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<ClienteResponseRest> getById(Long id) {
        ClienteResponseRest response = new ClienteResponseRest();
        try {
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró el cliente con ID " + id));

            List<Cliente> clientes = new ArrayList<>();
            clientes.add(cliente);
            response.getClienteResponse().setClientes(clientes);

            response.setMetadata("Ok", "00", "Éxito");
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.setMetadata("Respuesta nok", "-1", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al consultar");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<ClienteResponseRest> create(Cliente cliente) {
        ClienteResponseRest response = new ClienteResponseRest();
        try {
            Cliente clienteGuardado = clienteRepository.save(cliente);
            response.getClienteResponse().setClientes((List<Cliente>) clienteGuardado);
            response.setMetadata("Ok", "00", "Creado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al crear");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<ClienteResponseRest> update(Long id, Cliente cliente) {
        ClienteResponseRest response = new ClienteResponseRest();
        try {
            Cliente clienteExistente = clienteRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró el cliente con ID: " + id));
            if (null!= clienteExistente){
                cliente.setClienteId(id);
                Cliente clienteActualizado = clienteRepository.save(cliente);
                response.getClienteResponse().setClientes((List<Cliente>) clienteActualizado);
                response.setMetadata("Ok", "00", "Actualizado exitosamente");
            } else {
                response.setMetadata("Ok", "01", "No se encontró el cliente con ID: " + id);
            }
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.setMetadata("Respuesta nok", "-1", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al actualizar");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<ClienteResponseRest> delete(Long id) {
        ClienteResponseRest response = new ClienteResponseRest();
        try {
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró el cliente con ID " + id));
            clienteRepository.delete(cliente);
            response.setMetadata("Ok", "00", "Eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.setMetadata("Respuesta nok", "-1", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al eliminar");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
