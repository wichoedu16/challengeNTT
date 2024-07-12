package com.werp.bankApp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cuenta")
public class Cuenta {
    @Id
    @Column(name = "numero_cuenta", nullable = false, unique = true)
    private String numeroCuenta;
    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @Column(name = "cliente_id", insertable = false, updatable = false)
    private Long clienteId;

}
