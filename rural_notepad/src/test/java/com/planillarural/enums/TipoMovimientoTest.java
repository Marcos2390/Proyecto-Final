package com.planillarural.enums;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class TipoMovimientoTest {

    @Test
    void testNull() { // si es null devuelve null
        assertNull(TipoMovimiento.fromString(null));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "   " })
    void testBlancosDevuelvenNull(String valor) {
        assertNull(TipoMovimiento.fromString(valor)); // si es vacio devuelve null
    }

    @ParameterizedTest
    @ValueSource(strings = { "INGRESO", "ingreso", " InGrEsO ", "SALIDA", "salida", "  sAlIdA  " }) // valores validos
    void testValoresValidos(String valor) {
        TipoMovimiento esperado = TipoMovimiento.valueOf(valor.trim().toUpperCase()); // convierte el string al enum
                                                                                      // correspondiente
        assertEquals(esperado, TipoMovimiento.fromString(valor)); // verifica que el metodo fromString devuelve el enum
                                                                  // correcto
    }

    @ParameterizedTest
    @ValueSource(strings = { "entrada", "perro", "123", "ingresooo", "salidax" })
    void testValoresInvalidos(String valor) {
        assertNull(TipoMovimiento.fromString(valor));
    }

    @ParameterizedTest
    @EnumSource(TipoMovimiento.class)
    void tipoMovimientoNotNull(TipoMovimiento type) {
        assertNotNull(type);
    }

    @ParameterizedTest
    @EnumSource(TipoMovimiento.class)
    void stringConversionWorks(TipoMovimiento type) {
        assertEquals(type, TipoMovimiento.fromString(type.name()));
    }
}
