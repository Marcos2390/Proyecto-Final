package com.planillarural.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.planillarural.service.RegistroService;

class SanidadTest {

    private RegistroService service;

    @BeforeEach
    void setup() {
        service = new RegistroService();
    }

    @Test
    void registrarSanidad_creaCorrectamente() {
        Sanidad s = new Sanidad(10, "Aftosa", "01/05/2025");

        service.registrarSanidad(s);

        assertAll(
                () -> assertEquals(1, service.listarSanidad().size()), // verifica tamaño de la lista
                () -> assertEquals("Aftosa", service.listarSanidad().get(0).getVacuna()), // verifica vacuna
                () -> assertEquals("01/05/2025", s.getFecha())); // verifica fecha
    }
}
