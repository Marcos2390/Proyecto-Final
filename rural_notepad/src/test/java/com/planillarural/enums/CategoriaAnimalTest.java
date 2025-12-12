package com.planillarural.enums;

import static org.junit.jupiter.api.Assertions.assertEquals; //.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CategoriaAnimalTest {

    @Test
    void testNull() {
        // si es null devuelve OTRO
        assertEquals(CategoriaAnimal.OTRO, CategoriaAnimal.fromString(null));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "   ", "perro", "gato", "123", "VACUNOO" }) // valores inválidos
    void testInvalidosDevuelvenOTRO(String valor) {
        // si es inválido devuelve OTRO
        assertEquals(CategoriaAnimal.OTRO, CategoriaAnimal.fromString(valor));
    }

    @ParameterizedTest
    @ValueSource(strings = { "VACUNO", "OVINO", "CAPRINO", "EQUINO", "PORCINO", "OTRO" }) // valores válidos
    void testValoresValidos(String valor) {
        // si es válido devuelve el enum correspondiente
        assertEquals(CategoriaAnimal.valueOf(valor), CategoriaAnimal.fromString(valor));
    }
}
