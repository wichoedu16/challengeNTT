package com.werp.bankApp.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "cliente")
public class Cliente extends Persona {
    @Column(name = "cliente_id", unique = true, nullable = false)
    private Long clienteId;
    private String contrasenia;
    private Boolean estado;
}
