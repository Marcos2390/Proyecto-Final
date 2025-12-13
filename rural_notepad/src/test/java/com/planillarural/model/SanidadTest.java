package com.planillarural.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import com.planillarural.model.Sanidad;

class SanidadTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Test
    void registrarSanidad_creaCorrectamente() {
        Sanidad s = new Sanidad(1, "Aftosa", "01/01/2025");

        assertAll(
                () -> assertEquals(1, s.getIdAnimal()),
                () -> assertEquals("Aftosa", s.getVacuna()),
                () -> assertEquals(
                        LocalDate.parse("01/01/2025", FORMATTER),
                        s.getFecha()));
    }
}
