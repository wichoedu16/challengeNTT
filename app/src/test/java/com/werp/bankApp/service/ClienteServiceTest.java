package com.werp.bankApp.service;

import com.werp.bankApp.entity.Cliente;
import com.werp.bankApp.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PersonaService personaService;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setClienteId(1L);
        cliente.setNombre("John Doe");
    }

    @Test
    public void testGetAll() {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.getAll();
        assertEquals(1, result.size());
        assertEquals(cliente.getNombre(), result.get(0).getNombre());
    }

    @Test
    public void testGetById_ClienteExistente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.getById(1L);
        assertEquals(cliente.getNombre(), result.getNombre());
    }

    @Test
    public void testGetById_ClienteNoExistente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            clienteService.getById(1L);
        });

        assertEquals("No se encontró el cliente con ID 1", thrown.getMessage());
    }

    @Test
    public void testCreate() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente result = clienteService.create(cliente);
        assertEquals(cliente.getNombre(), result.getNombre());
    }

}