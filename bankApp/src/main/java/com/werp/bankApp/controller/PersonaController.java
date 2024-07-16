package com.werp.bankApp.controller;


import com.werp.bankApp.entity.Persona;
import com.werp.bankApp.service.PersonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping("")
    public ResponseEntity<List<Persona>> getAll() {
        List<Persona> personas = personaService.findAll();
        return ResponseEntity.ok(personas);
    }

    @GetMapping("/{id}")
    ResponseEntity<Persona> getById(@PathVariable Long id) {
        Persona persona = personaService.getById(id);
        return ResponseEntity.ok(persona);
    }
}
