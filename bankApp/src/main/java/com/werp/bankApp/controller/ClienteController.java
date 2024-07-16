package com.werp.bankApp.controller;

import com.werp.bankApp.entity.Cliente;
import com.werp.bankApp.response.ClienteResponseRest;
import com.werp.bankApp.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("")
    public ResponseEntity<ClienteResponseRest> getAll() {
        return clienteService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseRest> getById(@PathVariable Long id) {
        return clienteService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity<ClienteResponseRest> create(@RequestBody Cliente cliente) {
        return clienteService.create(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseRest> update(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.update(id, cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteResponseRest> delete(@PathVariable Long id) {
        return clienteService.delete(id);
    }
}
