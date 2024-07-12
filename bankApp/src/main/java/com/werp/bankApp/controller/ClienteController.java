package com.werp.bankApp.controller;

import com.werp.bankApp.entity.Cliente;
import com.werp.bankApp.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("")
    public ResponseEntity<List<Cliente>> getAll() {
        List<Cliente> clientes = clienteService.getAll();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    ResponseEntity<Cliente> getById(@PathVariable Long id) {
        Cliente cliente = clienteService.getById(id);
        return ResponseEntity.ok(cliente);
    }

    @ResponseStatus(value = HttpStatus.CREATED, reason = "Creado exitosamente")
    @PostMapping("")
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        Cliente clienteGuardado = clienteService.create(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteGuardado);
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Actualizado exitosamente")
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente clienteEditado = clienteService.update(id, cliente);
        return ResponseEntity.status(HttpStatus.OK).body(clienteEditado);
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Eliminado exitosamente")
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id){
        clienteService.delete(id);
    }
}
