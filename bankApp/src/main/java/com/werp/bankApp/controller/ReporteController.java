package com.werp.bankApp.controller;

import com.werp.bankApp.entity.Reporte;
import com.werp.bankApp.service.MovimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/reporte")
public class ReporteController {

    private final MovimientoService movimientoService;

    public ReporteController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<List<Reporte>> getByUsuario(@PathVariable Long clienteId) {
        List<Reporte> movimientos = movimientoService.getByUsuario(clienteId);
        return ResponseEntity.ok(movimientos);
    }
}
