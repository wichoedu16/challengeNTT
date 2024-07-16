package com.werp.bankApp.service;

import com.werp.bankApp.entity.Cliente;
import com.werp.bankApp.repository.ClienteRepository;
import com.werp.bankApp.response.ClienteResponseRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetById_Success() {
        Long id = 1L;
        Cliente cliente = new Cliente();
        cliente.setClienteId(id);
        cliente.setNombre("Cliente Test");

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        ResponseEntity<ClienteResponseRest> responseEntity = clienteService.getById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ClienteResponseRest response = responseEntity.getBody();
        assertEquals(1, response.getClienteResponse().getClientes().size());
        assertEquals("Cliente Test", response.getClienteResponse().getClientes().get(0).getNombre());

        verify(clienteRepository, times(1)).findById(id);
    }


    public void testGetById_EntityNotFoundException() {
        Long id = 2L;

        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> clienteService.getById(id));

        assertEquals("Error al obtener el cliente con ID " + id, exception.getMessage());
        verify(clienteRepository, times(1)).findById(id);
    }

}