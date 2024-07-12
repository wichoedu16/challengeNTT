package com.werp.bankApp.entity;

import javax.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table (name = "movimiento")
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movimiento_seq")
    @Column(name = "movimiento_id", nullable = false)
    private Long movimientoId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha;
    @Column(name = "tipo_movimiento", nullable = false)
    private String tipoMovimiento;
    private Double valor;
    private  Double saldo;

    @ManyToOne
    @JoinColumn(name = "numero_cuenta")
    private Cuenta cuenta;
    @Column(name = "numero_cuenta", insertable = false, updatable = false)
    private String numeroCuenta;

}
