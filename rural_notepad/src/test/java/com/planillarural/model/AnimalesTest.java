package com.planillarural.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.planillarural.enums.CategoriaAnimal;

@DisplayName("Tests unitarios de la clase Animales")
@Tag("unit")
class AnimalesTest {

    // =====================================================
    // VALIDACIÓN DE CARAVANA
    // =====================================================

    @ParameterizedTest(name = "Caravana válida: {0}")
    @ValueSource(ints = { 1, 5, 999 })
    @DisplayName("Crear animal con caravana válida")
    void validCaravansCreateAnimals(int caravana) {
        Animales a = new Animales(caravana, CategoriaAnimal.TORO, "Hereford", 2);

        assertAll("Validación de atributos",
                () -> assertEquals(caravana, a.getNumerocaravana(), "La caravana debe coincidir"),
                () -> assertEquals("Hereford", a.getRaza(), "La raza debe coincidir"),
                () -> assertTrue(a.getEdad() >= 0, "La edad no puede ser negativa"));
    }

    @Test
    @DisplayName("Caravana inválida lanza excepción")
    void invalidCaravanThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Animales(0, CategoriaAnimal.OVEJA, "Merino", 1),
                "Se esperaba IllegalArgumentException para caravana 0");
    }

    // =====================================================
    // VALIDACIÓN DE CATEGORÍA
    // =====================================================

    @ParameterizedTest(name = "Categoría: {0}")
    @EnumSource(CategoriaAnimal.class)
    @DisplayName("Categoría asignada correctamente")
    void categoryIsCorrect(CategoriaAnimal cat) {
        Animales a = new Animales(20, cat, "RazaX", 1);
        assertEquals(cat, a.getCategoria(), "La categoría debe coincidir con la esperada");
    }

    // =====================================================
    // ID AUTOINCREMENTAL
    // =====================================================

    @RepeatedTest(3)
    @DisplayName("ID autoincremental funciona correctamente")
    void idAutoIncrements() {
        Animales a1 = new Animales(100, CategoriaAnimal.TORO, "A", 1);
        Animales a2 = new Animales(101, CategoriaAnimal.TORO, "B", 1);

        assertTrue(a2.getId() > a1.getId(), "El segundo ID debe ser mayor que el primero");
    }

    // =====================================================
    // EQUALS Y HASHCODE
    // =====================================================

    @Test
    @DisplayName("Equals y hashCode comparan por número de caravana")
    void equalsWorksByCaravanNumber() {
        Animales a1 = new Animales(50, CategoriaAnimal.TORO, "ANGUS", 1);
        Animales a2 = new Animales(50, CategoriaAnimal.OVEJA, "MERINO", 3);

        assertAll("Comparación por caravana",
                () -> assertEquals(a1, a2, "Animales con misma caravana deben ser iguales"),
                () -> assertEquals(a1.hashCode(), a2.hashCode(), "Hashcodes deben coincidir"));
    }

    // =====================================================
    // RAZA NULA → CADENA VACÍA
    // =====================================================

    @Test
    @DisplayName("Raza nula se convierte en cadena vacía")
    void nullBreedBecomesEmptyString() {
        Animales a = new Animales(70, CategoriaAnimal.VACUNO, null, 3);
        assertEquals("", a.getRaza(), "Raza nula debe ser reemplazada por cadena vacía");
    }

    // =====================================================
    // EDAD NEGATIVA → 0
    // =====================================================

    @Test
    @DisplayName("Edad negativa se convierte en 0")
    void negativeAgeBecomesZero() {
        Animales a = new Animales(80, CategoriaAnimal.VACUNO, "Hereford", -5);
        assertEquals(0, a.getEdad(), "Edad negativa debe convertirse a 0");
    }

    // =====================================================
    // TO_STRING NO ROMPE
    // =====================================================

    @Test
    @DisplayName("toString no lanza excepciones y contiene 'caravana'")
    void toStringDoesNotThrow() {
        Animales a = new Animales(90, CategoriaAnimal.OVINO, "MERINO", 2);

        assertDoesNotThrow(() -> {
            String s = a.toString();
            assertTrue(s.contains("caravana"), "toString debe incluir la palabra 'caravana'");
        });
    }
}
