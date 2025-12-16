package com.planillarural.enums;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Tag("unit")
@Tag("smoke")
@Tag("regression")
class MenuOptionTest {

    // Test parametrizado para todos los códigos válidos
    @ParameterizedTest
    @Tag("regression")
    @CsvSource({ // code, esperado
            "1, AGREGAR_ANIMAL",
            "2, ELIMINAR_ANIMAL",
            "3, REGISTRAR_VACUNA",
            "4, REGISTRAR_MOVIMIENTO",
            "5, LISTAR_ANIMALES",
            "6, LISTAR_SANIDAD",
            "7, LISTAR_MOVIMIENTOS",
            "8, SEARCH_ANIMAL",
            "0, EXIT"
    })
    void testFromCodeValidos(int code, MenuOption esperado) { // verifica que el código devuelva el enum correcto
        assertEquals(esperado, MenuOption.fromCode(code));
    }

    // Test códigos inválidos
    @ParameterizedTest
    @CsvSource({ "-1", "10", "999", "9" }) //
    void testFromCodeInvalidos(int code) {
        assertNull(MenuOption.fromCode(code));
    }

    @Test
    void enumContieneTodosLosCodigosEsperados() {
        Set<Integer> codigosEsperados = Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8);

        Set<Integer> codigosEnum = Arrays.stream(MenuOption.values())
                .map(MenuOption::getCode)
                .collect(Collectors.toSet());

        assertEquals(codigosEsperados, codigosEnum);
    }
}
