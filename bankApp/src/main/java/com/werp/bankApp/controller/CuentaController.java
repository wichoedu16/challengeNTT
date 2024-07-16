package com.werp.bankApp.controller;

import com.werp.bankApp.entity.Cuenta;
import com.werp.bankApp.response.CuentaResponseRest;
import com.werp.bankApp.service.CuentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping("")
    public ResponseEntity<CuentaResponseRest> getAll() {
        return cuentaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponseRest> getById(@PathVariable Long id) {
        return cuentaService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity<CuentaResponseRest> create(@RequestBody Cuenta cuenta) {
        return cuentaService.create(cuenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaResponseRest> update(@PathVariable String id, @RequestBody Cuenta cuenta) {
        return cuentaService.update(id, cuenta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CuentaResponseRest> delete(@PathVariable String id) {
        return cuentaService.delete(id);
    }
}