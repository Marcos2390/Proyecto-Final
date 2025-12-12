package com.planillarural.model;

import com.planillarural.enums.TipoMovimiento;

public class Movimientos {
    private int idAnimal;
    private TipoMovimiento tipo;
    private String destino;
    private String fecha;

    public Movimientos(int idAnimal, TipoMovimiento tipo, String destino, String fecha) {
        this.idAnimal = idAnimal; // Guarda el número de identificación del animal asociado al movimiento.
        this.tipo = tipo; // Guarda el tipo de movimiento (INGRESO o SALIDA) utilizando el enum
                          // TipoMovimiento.
        this.destino = destino == null ? "" : destino; // Si el parámetro destino es null, se asigna un string vacío "".
                                                       // Si no es null, se asigna el valor proporcionado

        this.fecha = fecha == null ? "" : fecha; // Mismo comportamiento que destino, pero para la fecha del movimiento.
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public String getDestino() {
        return destino;
    }

    public String getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return String.format("Movimiento{idAnimal=%d, tipo=%s, destino=%s, fecha=%s}", idAnimal, tipo, destino, fecha); // aca
                                                                                                                        // el
                                                                                                                        // dato
                                                                                                                        // de
                                                                                                                        // la
                                                                                                                        // fecha
                                                                                                                        // es
                                                                                                                        // string
                                                                                                                        // porque
                                                                                                                        // usa
                                                                                                                        // "/"
                                                                                                                        // como
                                                                                                                        // separador
    }
}
