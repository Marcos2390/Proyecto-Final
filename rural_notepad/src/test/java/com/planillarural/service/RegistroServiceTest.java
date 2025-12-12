package com.planillarural.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.planillarural.enums.CategoriaAnimal;
import com.planillarural.enums.TipoMovimiento;
import com.planillarural.model.Animales;
import com.planillarural.model.Movimientos;
import com.planillarural.model.Sanidad;

@DisplayName("RegistroService - Test Suite")
class RegistroServiceTest {

    private RegistroService service;

    @BeforeEach
    void setup() {
        service = new RegistroService();
    }

    // ============================================================
    // ANIMALES
    // ============================================================

    @Test
    @Tag("unit")
    @DisplayName("agregarAnimal(): debe almacenar el animal correctamente")
    void agregarAnimal_loGuardaCorrectamente() {

        Animales a = new Animales(10, CategoriaAnimal.VACUNO, "Hereford", 3);
        service.agregarAnimal(a);

        Animales buscado = service.buscarAnimalPorCaravana(10);

        assertAll(
                () -> assertNotNull(buscado, "El animal debería existir"),
                () -> assertEquals(10, buscado.getNumerocaravana()),
                () -> assertEquals("Hereford", buscado.getRaza()));
    }

    @Test
    @Tag("unit")
    @DisplayName("eliminarAnimalPorCaravana(): debe devolver TRUE si el animal existía")
    void eliminarAnimalPorCaravana_devuelveTrueSiExiste() {

        service.agregarAnimal(new Animales(5, CategoriaAnimal.OVINO, "Merino", 2));

        boolean result = service.eliminarAnimalPorCaravana(5);

        assertTrue(result, "Debe devolver TRUE al eliminar un animal existente");
    }

    @Test
    @Tag("unit")
    @DisplayName("eliminarAnimalPorCaravana(): debe devolver FALSE si el animal no existe")
    void eliminarAnimalPorCaravana_devuelveFalseSiNoExiste() {

        boolean result = service.eliminarAnimalPorCaravana(999);

        assertFalse(result, "Debe devolver FALSE si la caravana no está registrada");
    }

    @Test
    @Tag("unit")
    @DisplayName("listarAnimales(): debe devolver una lista independiente")
    void listarAnimales_retornaCopiaIndependiente() {

        service.agregarAnimal(new Animales(1, CategoriaAnimal.VACUNO, "A", 1));

        List<Animales> lista = service.listarAnimales();
        lista.clear(); // NO debe afectar la lista verdadera

        assertEquals(1, service.listarAnimales().size(),
                "La lista interna no debe verse afectada");
    }

    // ============================================================
    // MOVIMIENTOS
    // ============================================================

    @Test
    @Tag("unit")
    @DisplayName("registrarMovimiento(): debe permitir un INGRESO sin validar existencia del animal")
    void registrarMovimiento_ingresoSinAnimal_loAgregaEnLaLista() {

        Movimientos mov = new Movimientos(
                100, TipoMovimiento.INGRESO, "Compra", "01/01/2025");

        assertDoesNotThrow(() -> service.registrarMovimiento(mov));

        assertEquals(1, service.listarMovimientos().size());
    }

    @Test
    @Tag("unit")
    @DisplayName("registrarMovimiento(): SALIDA debe permitirse si el animal existe")
    void registrarMovimiento_salidaConAnimalExistente_funciona() {

        service.agregarAnimal(new Animales(20, CategoriaAnimal.VACUNO, "Hereford", 2));

        Movimientos mov = new Movimientos(
                20, TipoMovimiento.SALIDA, "Venta", "02/02/2025");

        assertDoesNotThrow(() -> service.registrarMovimiento(mov));

        assertEquals(1, service.listarMovimientos().size());
    }

    @Test
    @Tag("unit")
    @DisplayName("registrarMovimiento(): SALIDA debe lanzar error si el animal NO existe")
    void registrarMovimiento_salidaSinAnimal_lanzaError() {

        Movimientos mov = new Movimientos(
                999, TipoMovimiento.SALIDA, "Error", "10/10/2025");

        Exception e = assertThrows(IllegalArgumentException.class,
                () -> service.registrarMovimiento(mov));

        assertTrue(e.getMessage().contains("does not exist"),
                "El mensaje debe indicar que el animal no existe");
    }

    @Test
    @Tag("unit")
    @DisplayName("listarMovimientos(): debe retornar una copia independiente")
    void listarMovimientos_retornaCopiaIndependiente() {

        service.registrarMovimiento(
                new Movimientos(1, TipoMovimiento.INGRESO, "Destino", "10/10/2025"));

        List<Movimientos> copia = service.listarMovimientos();
        copia.clear();

        assertEquals(1, service.listarMovimientos().size());
    }

    // ============================================================
    // SANIDAD
    // ============================================================

    @Test
    @Tag("unit")
    @DisplayName("registrarSanidad(): debe guardar correctamente el registro")
    void registrarSanidad_guardaCorrectamente() {

        Sanidad s = new Sanidad(10, "Aftosa", "01/03/2025");
        service.registrarSanidad(s);

        List<Sanidad> lista = service.listarSanidad();

        assertAll(
                () -> assertEquals(1, lista.size()),
                () -> assertEquals("Aftosa", lista.get(0).getVacuna()));
    }

    @Test
    @Tag("unit")
    @DisplayName("listarSanidad(): debe retornar una copia independiente")
    void listarSanidad_retornaCopiaIndependiente() {

        service.registrarSanidad(new Sanidad(1, "Vacuna X", "01/01/2025"));

        List<Sanidad> copia = service.listarSanidad();
        copia.clear();

        assertEquals(1, service.listarSanidad().size());
    }
}