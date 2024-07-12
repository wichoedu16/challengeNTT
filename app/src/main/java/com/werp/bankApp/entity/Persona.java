package com.werp.bankApp.entity;

import lombok.*;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
public class Persona {
    @Id
    @Column(name = "persona_id")
    private Long personaId;
    private String nombre;
    private String genero;
    private int edad;
    private String identificacion;
    private String direccion;
    private String telefono;

}
