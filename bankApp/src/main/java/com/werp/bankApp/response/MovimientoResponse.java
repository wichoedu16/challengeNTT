package com.werp.bankApp.response;

import com.werp.bankApp.entity.Movimiento;
import lombok.Data;

import java.util.List;

@Data
public class MovimientoResponse {
    private List<Movimiento> movimientos;
}
