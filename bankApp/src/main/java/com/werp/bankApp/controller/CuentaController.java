package com.werp.bankApp.controller;

import com.werp.bankApp.entity.Cuenta;
import com.werp.bankApp.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;
    @GetMapping("")
    public ResponseEntity<List<Cuenta>> getAll() {
        List<Cuenta> cuentas = cuentaService.getAll();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{id}")
    ResponseEntity<Cuenta> getById(@PathVariable Long id) {
        Cuenta cuenta = cuentaService.getById(id);
        return ResponseEntity.ok(cuenta);
    }

    @ResponseStatus(value = HttpStatus.CREATED, reason = "Creado exitosamente")
    @PostMapping("")
    public ResponseEntity<Cuenta> create(@RequestBody Cuenta cuenta) {
        Cuenta cuentaGuardado = cuentaService.create(cuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaGuardado);
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Actualizado exitosamente")
    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> update(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        Cuenta cuentaEditado = cuentaService.update(id, cuenta);
        return ResponseEntity.status(HttpStatus.OK).body(cuentaEditado);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        cuentaService.delete(id);
    }
}