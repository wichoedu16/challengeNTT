package com.werp.bankApp.response;

import com.werp.bankApp.entity.Cliente;
import lombok.Data;

import java.util.List;

@Data
public class ClienteResponse {
    private List<Cliente> clientes;
}
