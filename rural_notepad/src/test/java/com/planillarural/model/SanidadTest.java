package com.planillarural.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SanidadTest {

    @Test
    void sanidadStoresDataCorrectly() {
        Sanidad s = new Sanidad(10, "Vacuna Aftosa", "01/05/2025");

        assertAll(
                () -> assertEquals(10, s.getIdAnimal()),
                () -> assertEquals("Vacuna Aftosa", s.getVacuna()),
                () -> assertEquals("01/05/2025", s.getFecha()));
    }
}
