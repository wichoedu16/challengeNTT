package com.werp.bankApp.response;

import com.werp.bankApp.entity.Cuenta;
import lombok.Data;

import java.util.List;

@Data
public class CuentaResponse {
    private List<Cuenta> cuentas;
}
