package com.werp.bankApp.service;

import com.werp.bankApp.entity.Cuenta;
import com.werp.bankApp.repository.CuentaRepository;
import com.werp.bankApp.response.ClienteResponseRest;
import com.werp.bankApp.response.CuentaResponseRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteService clienteService;

    public CuentaService(CuentaRepository cuentaRepository, ClienteService clienteService) {
        this.cuentaRepository = cuentaRepository;
        this.clienteService = clienteService;
    }

    public ResponseEntity<CuentaResponseRest> getAll() {
        CuentaResponseRest response = new CuentaResponseRest();
        try {
            List<Cuenta> cuentas = cuentaRepository.findAll();
            response.getCuentaResponse().setCuentas(cuentas);
            response.setMetadata("Ok", "00", "Exito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al consultar");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<CuentaResponseRest> getById(Long id) {
        CuentaResponseRest response = new CuentaResponseRest();
        try {
            Cuenta cuenta = cuentaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No se encuentra la cuentaID: " + id));
            response.getCuentaResponse().setCuentas((List<Cuenta>) cuenta);
            response.setMetadata("Ok", "00", "Exito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al consultar");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<CuentaResponseRest> getByClienteId(Long clienteId) {
        CuentaResponseRest response = new CuentaResponseRest();
        try {
            List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);
            response.getCuentaResponse().setCuentas(cuentas);
            response.setMetadata("Ok", "00", "Exito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "No se encontró la cuenta del cliente ID " + clienteId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public Cuenta getByNumeroCuenta(String numeroCuenta) {
        CuentaResponseRest response = new CuentaResponseRest();
        Optional<Cuenta> cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
        return cuenta.orElse(null);
    }

    public ResponseEntity<CuentaResponseRest> create(Cuenta cuenta) {
        CuentaResponseRest response = new CuentaResponseRest();
        try {
            if(validarCliente(cuenta.getClienteId()) && null!=cuenta.getClienteId()){
                Cuenta cuentaGuardada = cuentaRepository.save(cuenta);
                response.getCuentaResponse().setCuentas((List<Cuenta>) cuentaGuardada);
                response.setMetadata("Ok", "00", "Creado Exitosamente");
                return ResponseEntity.ok(response);
            }else{
                response.setMetadata("nok", "01", "No se encontró el cliente: " + cuenta.getClienteId());
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al crear");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<CuentaResponseRest> update(String numeroCuenta, Cuenta cuenta) {
        CuentaResponseRest response = new CuentaResponseRest();
        try {
            Optional<Cuenta> cuentaExistente = cuentaRepository.findByNumeroCuenta(numeroCuenta);
            if (cuentaExistente.isPresent()) {
                Cuenta cuentaActualizada = cuentaRepository.save(cuenta);
                response.getCuentaResponse().setCuentas((List<Cuenta>) cuentaActualizada);
                response.setMetadata("Ok", "00", "Actualizado Exitosamente");
                return ResponseEntity.ok(response);
            }else{
                response.setMetadata("nok", "01", "No se encontró cuenta");
                return ResponseEntity.ok(response);
            }
        } catch (EntityNotFoundException e) {
            response.setMetadata("Respuesta nok", "-1", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al actualizar");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<CuentaResponseRest>  delete(String id) {
        CuentaResponseRest response = new CuentaResponseRest();
        try{
            Optional<Cuenta> cuenta = cuentaRepository.findByNumeroCuenta(id);
            if (cuenta.isPresent()) {
                cuentaRepository.delete(cuenta.get());
                response.setMetadata("Ok", "00", "Eliminado exitosamente");
                return ResponseEntity.ok(response);
            }else {
                response.setMetadata("nok", "01", "Error al eliminar");
                return ResponseEntity.ok(response);
            }
        }catch (EntityNotFoundException e) {
            response.setMetadata("Respuesta nok", "-1", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al eliminar");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private boolean validarCliente(Long clienteId) {
        Boolean validacion = false;
        ResponseEntity<ClienteResponseRest> cliente = clienteService.getById(clienteId);
        if (Objects.nonNull(cliente)){
            validacion = true;
        }
        return validacion;
    }
}