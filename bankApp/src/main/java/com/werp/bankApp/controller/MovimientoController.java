package com.werp.bankApp.controller;

import com.werp.bankApp.entity.Movimiento;
import com.werp.bankApp.response.MovimientoResponseRest;
import com.werp.bankApp.service.MovimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping("")
    public ResponseEntity<MovimientoResponseRest> getAll(){
        return movimientoService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponseRest> getById(@PathVariable Long id) {
        return movimientoService.getById(id);
    }

    @PostMapping("")
    public ResponseEntity<MovimientoResponseRest> create(@RequestBody Movimiento movimiento) {
        return movimientoService.create(movimiento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MovimientoResponseRest> delete(@PathVariable Long id){
        return movimientoService.delete(id);
    }

}