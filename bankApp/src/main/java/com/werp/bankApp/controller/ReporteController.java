package com.werp.bankApp.controller;

import com.werp.bankApp.entity.Movimiento;
import com.werp.bankApp.entity.Reporte;
import com.werp.bankApp.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/reporte")
public class ReporteController {

    @Autowired
    private MovimientoService movimientoService;
    @GetMapping("/{clienteId}")
    public ResponseEntity<List<Reporte>> getByUsuario(@PathVariable Long clienteId) {
        List<Reporte> movimientos = movimientoService.getByUsuario(clienteId);
        return ResponseEntity.ok(movimientos);
    }
}
