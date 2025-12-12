package com.planillarural.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class SanidadTest {

    @Test
    void sanidadStoresDataCorrectly() { // prueba del constructor de Sanidad
        Sanidad s = new Sanidad(10, "Vacuna Aftosa", "01/05/2025");

        assertAll(
                () -> assertEquals(10, s.getIdAnimal()), // verifica idAnimal
                () -> assertEquals("Vacuna Aftosa", s.getVacuna()), // verifica vacuna
                () -> assertEquals("01/05/2025", s.getFecha())); // verifica fecha
    }
}
