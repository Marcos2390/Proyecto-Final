package com.planillarural.model;

import com.planillarural.enums.TipoMovimiento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovimientosTest {

    @Test
    void movementConstructsProperly() { // prueba del constructor de Movimientos
        Movimientos m = new Movimientos(10, TipoMovimiento.INGRESO, "Compra", "10/10/2025");

        assertAll(
                () -> assertEquals(10, m.getIdAnimal()), // verifica idAnimal
                () -> assertEquals(TipoMovimiento.INGRESO, m.getTipo()), // verifica tipo
                () -> assertEquals("Compra", m.getDestino()), // verifica destino
                () -> assertEquals("10/10/2025", m.getFecha())); // verifica fecha
    }
}
