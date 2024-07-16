package com.werp.bankApp.service;

import com.werp.bankApp.entity.Cliente;
import com.werp.bankApp.entity.Cuenta;
import com.werp.bankApp.entity.Movimiento;
import com.werp.bankApp.entity.Reporte;
import com.werp.bankApp.repository.MovimientoRepository;
import com.werp.bankApp.response.ClienteResponseRest;
import com.werp.bankApp.response.CuentaResponseRest;
import com.werp.bankApp.response.MovimientoResponseRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovimientoService {
    public static final String RETIRO = "Retiro";
    public static final String DEPOSITO = "Deposito";
    private final MovimientoRepository movimientoRepository;
    private final CuentaService cuentaService;
    private final  ClienteService clienteService;

    public MovimientoService(MovimientoRepository movimientoRepository, CuentaService cuentaService, ClienteService clienteService) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaService = cuentaService;
        this.clienteService = clienteService;
    }

    public ResponseEntity<MovimientoResponseRest> getAll() {
        MovimientoResponseRest response = new MovimientoResponseRest();
        try {
            List<Movimiento> movimientos = movimientoRepository.findAll();
            response.getMovimientoResponse().setMovimientos(movimientos);
            response.setMetadata("Ok", "00", "Éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al consultar");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<MovimientoResponseRest> getById(Long id) {
        MovimientoResponseRest response = new MovimientoResponseRest();
        try {
            Movimiento movimiento = movimientoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No se encuentra el movimiento: " + id));
            response.getMovimientoResponse().setMovimientos((List<Movimiento>) movimiento);
            response.setMetadata("Ok", "00", "Éxito");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al consultar");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<MovimientoResponseRest>  create(Movimiento movimiento) {
        MovimientoResponseRest response = new MovimientoResponseRest();
        try{
            if(validarCuenta(movimiento.getNumeroCuenta()) && null!=movimiento.getNumeroCuenta()){
                movimiento.setCuenta(cuentaService.getByNumeroCuenta(movimiento.getNumeroCuenta()));
                movimiento.setSaldo(movimiento.getCuenta().getSaldoInicial());
                if (RETIRO.equals(movimiento.getTipoMovimiento())) {
                    Double saldoInicial = validarSaldo(movimiento.getCuenta(), movimiento.getValor(), RETIRO);
                    movimiento.getCuenta().setSaldoInicial(saldoInicial);
                }else if (DEPOSITO.equals(movimiento.getTipoMovimiento())){
                    Double saldoInicial = validarSaldo(movimiento.getCuenta(), movimiento.getValor(), DEPOSITO);
                    movimiento.getCuenta().setSaldoInicial(saldoInicial);
                }
                Movimiento movimientoGuardado = movimientoRepository.save(movimiento);
                response.getMovimientoResponse().setMovimientos((List<Movimiento>) movimientoGuardado);
                response.setMetadata("Ok", "00", "Creado Exitosamente");
            } else {
                response.setMetadata("nok", "01", "No se encontró numero de cuenta: " + movimiento.getNumeroCuenta());
            }
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.setMetadata("Respuesta nok", "-1", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (ArithmeticException e) {
            response.setMetadata("Respuesta nok", "-2", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private Double validarSaldo(Cuenta cuenta, Double valor, String tipoMovimiento) {
        Double saldo = 0D;
        Double saldoInicial = cuenta.getSaldoInicial();

        if(RETIRO.equals(tipoMovimiento)){
            if (saldoInicial <= saldo){
                throw new ArithmeticException("Saldo no disponible.");
            }else if ((saldoInicial - valor) >= 0){
                saldo = saldoInicial - valor;
            } else {
                throw new ArithmeticException("Saldo actual: "+ saldoInicial + ". Valor solicitado: "+ valor +"Saldo no disponible.");
            }
        }else if (DEPOSITO.equals(tipoMovimiento)){
            saldo = saldoInicial + valor;
        }
        return saldo;
    }

    private boolean validarCuenta(String numeroCuenta) {
        Cuenta cuenta = cuentaService.getByNumeroCuenta(numeroCuenta);
        return Objects.nonNull(cuenta);
    }

    public ResponseEntity<MovimientoResponseRest> delete(Long id) {
        MovimientoResponseRest response = new MovimientoResponseRest();
        try{
            Movimiento movimiento = movimientoRepository.findById(id).
                    orElseThrow(()-> new EntityNotFoundException("No se encontró"));
            movimientoRepository.delete(movimiento);
            response.setMetadata("Ok", "00", "Eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.setMetadata("Respuesta nok", "-1", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setMetadata("Respuesta nok", "-1", "Error al eliminar");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public List<Reporte> getByUsuario(Long clienteId) {
        ResponseEntity<ClienteResponseRest> clienteResponseEntity = clienteService.getById(clienteId);

        if (!clienteResponseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error al obtener el cliente con ID " + clienteId);
        }

        ClienteResponseRest clienteResponseRest = clienteResponseEntity.getBody();
        if (clienteResponseRest == null || clienteResponseRest.getClienteResponse() == null) {
            throw new RuntimeException("Respuesta nula al obtener el cliente con ID " + clienteId);
        }

        List<Cliente> clientes = clienteResponseRest.getClienteResponse().getClientes();
        if (clientes.isEmpty()) {
            throw new RuntimeException("No se encontró el cliente con ID " + clienteId);
        }

        String nombreCliente = clientes.get(0).getNombre();

        ResponseEntity<CuentaResponseRest> cuentaResponseEntity = cuentaService.getByClienteId(clienteId);

        if (!cuentaResponseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error al obtener cuentas del cliente con ID " + clienteId);
        }

        CuentaResponseRest cuentaResponseRest = cuentaResponseEntity.getBody();
        if (cuentaResponseRest == null || cuentaResponseRest.getCuentaResponse() == null) {
            throw new RuntimeException("Respuesta nula al obtener cuentas del cliente con ID " + clienteId);
        }

        List<Cuenta> cuentas = cuentaResponseRest.getCuentaResponse().getCuentas();
        if (cuentas == null) {
            throw new RuntimeException("Cuentas nulas al obtener cuentas del cliente con ID " + clienteId);
        }

        return cuentas.stream()
                .flatMap(cuenta -> crearReportesPorCuenta(cuenta, nombreCliente).stream())
                .collect(Collectors.toList());
    }

    private List<Reporte> crearReportesPorCuenta(Cuenta cuenta, String nombreCliente) {
        List<Movimiento> movimientos = movimientoRepository.findByNumeroCuenta(cuenta.getNumeroCuenta());

        return movimientos.stream()
                .filter(movimiento -> movimiento.getFecha() != null)
                .map(movimiento -> crearReporte(cuenta, nombreCliente, movimiento))
                .collect(Collectors.toList());
    }

    private Reporte crearReporte(Cuenta cuenta, String nombreCliente, Movimiento movimiento) {
        Reporte reporte = new Reporte();
        reporte.setCliente(nombreCliente);
        reporte.setNumeroCuenta(cuenta.getNumeroCuenta());
        reporte.setTipoCuenta(cuenta.getTipoCuenta());
        reporte.setEstado(cuenta.getEstado());
        reporte.setSaldoInicial(cuenta.getSaldoInicial());
        reporte.setSaldoDisponible(movimiento.getSaldo());
        reporte.setFecha(movimiento.getFecha());
        reporte.setMovimiento(movimiento.getValor());
        return reporte;
    }

}
