package com.werp.bankApp.service;

import com.werp.bankApp.entity.Cliente;
import com.werp.bankApp.entity.Cuenta;
import com.werp.bankApp.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    CuentaRepository cuentaRepository;
    @Autowired
    ClienteService clienteService;

    public List<Cuenta> getAll() {
        return cuentaRepository.findAll();
    }

    public Cuenta getById(Long id) {
        Optional<Cuenta> cuenta = cuentaRepository.findById(id);
        if (cuenta.isPresent()) {
            return cuenta.get();
        } else {
            throw new EntityNotFoundException("No se encontró la cuenta con ID " + id);
        }
    }

    public List<Cuenta> getByClienteId(Long clienteId) {
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);
        if (cuentas.size() > 1) {
            return cuentas;
        } else {
            throw new EntityNotFoundException("No se encontró la cuenta del cliente ID " + clienteId);
        }
    }

    public Cuenta getByNumeroCuenta(String numeroCuenta) {
        Optional<Cuenta> cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
        if (cuenta.isPresent()) {
            return cuenta.get();
        } else {
            throw new EntityNotFoundException("No se encontró la cuenta número " + numeroCuenta);
        }
    }

    public Cuenta create(Cuenta cuenta) {
        if(validarCliente(cuenta.getClienteId()) && null!=cuenta.getClienteId()){
            cuenta.setCliente(clienteService.getById(cuenta.getClienteId()));
            return cuentaRepository.save(cuenta);
        }else{
            throw new NoResultException();
        }
    }

    private boolean validarCliente(Long clienteId) {
        Boolean validacion = false;
        Cliente cliente = clienteService.getById(clienteId);
        if (Objects.nonNull(cliente)){
            validacion = true;
        }
        return validacion;
    }

    public Cuenta update(Long id, Cuenta cuenta) {
        Optional<Cuenta> cuentaEncontrada = cuentaRepository.findById(id);
        if (cuentaEncontrada.isPresent()) {
            return cuentaRepository.save(cuentaEncontrada.get());
        }else {
            throw new EntityNotFoundException("No se encontró la cuenta con ID " + id);
        }
    }
    public void delete(Long id) {
        Optional<Cuenta> cuenta = cuentaRepository.findById(id);
        if (cuenta.isPresent()) {
            cuentaRepository.delete(cuenta.get());
        }else {
            throw new EntityNotFoundException("No se encontró la cuenta con ID " + id);
        }
    }


}
