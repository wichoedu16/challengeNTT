package com.werp.bankApp.controller;

import com.werp.bankApp.entity.Movimiento;
import com.werp.bankApp.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    @Autowired
    private MovimientoService movimientoService;

    @GetMapping("")
    public ResponseEntity<List<Movimiento>> getAll(){
        List<Movimiento> movimientos = movimientoService.getAll();
        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/{id}")
    ResponseEntity<Movimiento> getById(@PathVariable Long id) {
        Movimiento movimiento = movimientoService.getById(id);
        return ResponseEntity.ok(movimiento);
    }

    @ResponseStatus(value = HttpStatus.CREATED, reason = "Movimiento creado exitosamente")
    @PostMapping("")
    public ResponseEntity<Movimiento> create(@RequestBody Movimiento movimiento) {
        Movimiento movimientoGuardado = movimientoService.create(movimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoGuardado);
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Actualizado exitosamente")
    @PutMapping("/{id}")
    public ResponseEntity<Movimiento> update(@PathVariable Long id, @RequestBody Movimiento movimiento) {
        Movimiento movimientoEditado = movimientoService.update(id, movimiento);
        return ResponseEntity.status(HttpStatus.OK).body(movimientoEditado);
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Eliminado exitosamente")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        movimientoService.delete(id);
    }

}