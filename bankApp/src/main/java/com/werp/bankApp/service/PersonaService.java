package com.werp.bankApp.service;

import com.werp.bankApp.entity.Persona;
import com.werp.bankApp.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    PersonaRepository personaRepository;

    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    public Persona getById(Long id) {
        Optional<Persona> persona = personaRepository.findById(id);
        if (persona.isPresent()) {
            return persona.get();
        } else {
            throw new EntityNotFoundException("No se encontró la persona con ID " + id);
        }
    }
}
