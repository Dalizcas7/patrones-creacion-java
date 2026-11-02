# Patrones de creación en Java: Abstract Factory

## Descripción
Este proyecto implementa el patrón de diseño creacional **Abstract Factory** para procesar informes de diferentes categorías y tipos.

## Categorías de informes
- ENTRADA
- SALIDA
- MIXTA

## Tipos de informes
- FACTURA
- COMPRA
- PEDIDO

El tipo PEDIDO está permitido exclusivamente para la categoría ENTRADA.

## Formato de los archivos
Los archivos utilizan la siguiente estructura:

`CAT_TIPO_nombre.txt`

Ejemplos:
- `ENT_FAC_001.txt`
- `SAL_COM_001.txt`
- `MIX_FAC_001.txt`
- `ENT_PED_001.txt`

## Requisitos
- Java JDK 8 o superior.

## Ejecución

Compilar:

```bash
javac Main.java
```

Ejecutar:

```bash
java Main
```

## Pruebas
El programa verifica el procesamiento de las categorías y tipos permitidos, incluyendo el caso especial de PEDIDO y el manejo de archivos no válidos.

## Patrón implementado
Abstract Factory, mediante fábricas concretas que permiten crear procesadores de informes según la categoría correspondiente.
