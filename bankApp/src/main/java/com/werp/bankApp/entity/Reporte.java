package com.werp.bankApp.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Reporte {

    LocalDate fecha;
    String cliente;
    String numeroCuenta;
    String tipoCuenta;
    Double saldoInicial;
    Boolean estado;
    Double movimiento;
    Double saldoDisponible;

}
