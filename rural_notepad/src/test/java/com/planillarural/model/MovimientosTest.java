package com.planillarural.model;

import com.planillarural.enums.TipoMovimiento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovimientosTest {

    @Test
    void movementConstructsProperly() {
        Movimientos m = new Movimientos(10, TipoMovimiento.INGRESO, "Compra", "10/10/2025");

        assertAll(
                () -> assertEquals(10, m.getIdAnimal()),
                () -> assertEquals(TipoMovimiento.INGRESO, m.getTipo()),
                () -> assertEquals("Compra", m.getDestino()),
                () -> assertEquals("10/10/2025", m.getFecha()));
    }
}
