# pruebaTecnicaER
## Descripcion
Creacion de clientes, cuentas de usuario, movimientos y generación de reporte en base a un cliente sugerido.

## Tech stack
- Spring Boot 2.7.11
- Java 8
- MySql 8
- Postman

## Instrucciones
 - Ejecutar el script
 ```bash 
./run-app.sh
```

 -Abrir postamn y ejecutar la peticion GET 
```bash 
curl --location 'localhost:8080/clientes'
```

## Response
 ```json 
[
  {
    "personaId": 1,
    "nombre": "Jose Lema",
    "genero": "Masculino",
    "edad": 20,
    "identificacion": "1722334400",
    "direccion": "Otavalo sn y principal",
    "telefono": "098254785",
    "clienteId": 1,
    "contrasenia": "1234",
    "estado": true
  },
  {
    "personaId": 2,
    "nombre": "Marianela Montalvo",
    "genero": "Femenino",
    "edad": 21,
    "identificacion": "1722334411",
    "direccion": "Amazonas y NNUU",
    "telefono": "097548965",
    "clienteId": 2,
    "contrasenia": "5678",
    "estado": true
  },
  {
    "personaId": 3,
    "nombre": "Juan Osorio",
    "genero": "Masculino",
    "edad": 22,
    "identificacion": "1722334422",
    "direccion": "13 junio y Equinoccial",
    "telefono": "098874587",
    "clienteId": 3,
    "contrasenia": "1245",
    "estado": true
  }
]
 ```