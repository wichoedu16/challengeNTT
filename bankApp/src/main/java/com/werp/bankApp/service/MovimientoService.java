package com.werp.bankApp.service;

import com.werp.bankApp.entity.Cliente;
import com.werp.bankApp.entity.Cuenta;
import com.werp.bankApp.entity.Movimiento;
import com.werp.bankApp.entity.Reporte;
import com.werp.bankApp.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MovimientoService {
    public static final String RETIRO = "Retiro";
    public static final String DEPOSITO = "Deposito";
    @Autowired
    MovimientoRepository movimientoRepository;
    @Autowired
    CuentaService cuentaService;
    @Autowired
    ClienteService clienteService;

    public List<Movimiento> getAll() {
        return movimientoRepository.findAll();
    }

    public Movimiento getById(Long id) {
        Optional<Movimiento> movimiento = movimientoRepository.findById(id);
        if (movimiento.isPresent()) {
            return movimiento.get();
        } else {
            throw new EntityNotFoundException("No se encontró el movimiento con ID " + id);
        }
    }

    public Movimiento create(Movimiento movimiento) {
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

            return movimientoRepository.save(movimiento);
        }
        throw new EntityNotFoundException("No se encontró la cuenta número" + movimiento.getNumeroCuenta());
    }

    private String validarTransaccion(String tipoMovimiento) {
        String validacion = "";
        if (RETIRO.equals(tipoMovimiento)){
             validacion = "-";
        } else if (DEPOSITO.equals(tipoMovimiento)) {
            validacion = "";
        }
        return validacion;
    }

    private Double validarSaldo(Cuenta cuenta, Double valor, String tipoMovimiento) {
        Double saldo = 0D;
        Double saldoInicial = cuenta.getSaldoInicial();

        if(RETIRO.equals(tipoMovimiento)){
            if (saldoInicial <= saldo){
                throw new ArithmeticException("El saldo es menor a cero");
            }else{
                saldo = saldoInicial - valor;
            }
        }else if (DEPOSITO.equals(tipoMovimiento)){
            saldo = saldoInicial + valor;
        }
        return saldo;
    }

    private boolean validarCuenta(String numeroCuenta) {
        Boolean validacion = false;
        Cuenta cuenta = cuentaService.getByNumeroCuenta(numeroCuenta);
        if (Objects.nonNull(cuenta)){
            validacion = true;
        }
        return validacion;
    }

    public Movimiento update(Long id, Movimiento movimiento) {
        Optional<Movimiento> movimientoEncontrado = movimientoRepository.findById(id);
        if (movimientoEncontrado.isPresent()) {
            return movimientoRepository.save(movimiento);
        } else {
            throw new EntityNotFoundException("No se encontró el movimiento con ID " + id);
        }
    }

    public void delete(Long id) {
        Optional<Movimiento> movimiento = movimientoRepository.findById(id);
        if (movimiento.isPresent()) {
            movimientoRepository.delete(movimiento.get());
        } else {
            throw new EntityNotFoundException("No se encontró el movimiento con ID " + id);
        }
    }

    public List<Reporte> getByUsuario(Long clienteId) {
        List<Reporte> reportes = new ArrayList<>();
        Cliente cliente = clienteService.getById(clienteId);
        if(Objects.nonNull(cliente)){
            String nombreCliente = cliente.getNombre();
            List<Cuenta> cuentas = cuentaService.getByClienteId(clienteId);
            if (cuentas.size() > 0){
                for (Cuenta cuenta : cuentas) {
                    Reporte reporte = new Reporte();
                    reporte.setCliente(nombreCliente);
                    reporte.setNumeroCuenta(cuenta.getNumeroCuenta());
                    reporte.setTipoCuenta(cuenta.getTipoCuenta());
                    reporte.setEstado(cuenta.getEstado());
                    reporte.setSaldoDisponible(cuenta.getSaldoInicial());
                    List<Movimiento> movimientos = movimientoRepository.findByNumeroCuenta(cuenta.getNumeroCuenta());
                    for (Movimiento movimiento : movimientos){
                        if (null != movimiento.getFecha()){
                            reporte.setSaldoInicial(movimiento.getSaldo());
                            reporte.setFecha(movimiento.getFecha());
                            reporte.setMovimiento(movimiento.getValor());
                            reportes.add(reporte);
                        }
                    }
                }
            }
        }
        return reportes;
    }
}
