package com.planillarural.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.planillarural.enums.CategoriaAnimal;
import com.planillarural.enums.TipoMovimiento;
import com.planillarural.model.Animales;
import com.planillarural.model.Movimientos;
import com.planillarural.model.Sanidad;

@Tag("regression")
@Tag("Smoke")
@DisplayName("RegistroService - Unit Tests")
class RegistroServiceTest {

    private RegistroService service;

    @BeforeEach
    void setUp() {
        service = new RegistroService(false);
    }

    // ============================================================
    // ANIMALES
    // ============================================================

    @Test
    @Tag("smoke")
    @Tag("regression")

    @DisplayName("agregarAnimal(): debe almacenar el animal correctamente")
    void agregarAnimal_loGuardaCorrectamente() {

        Animales a = new Animales(10, CategoriaAnimal.VACUNO, "Hereford", 3);
        service.agregarAnimal(a);

        Animales buscado = service.buscarAnimalPorCaravana(10);

        assertAll(() -> assertNotNull(buscado, "El animal debería existir"),
                () -> assertEquals(10, buscado.getNumerocaravana()),
                () -> assertEquals("Hereford", buscado.getRaza()));
    }

    @Test
    @Tag("regression")

    @DisplayName("eliminarAnimalPorCaravana(): debe devolver TRUE si el animal existía")
    void eliminarAnimalPorCaravana_devuelveTrueSiExiste() {

        service.agregarAnimal(new Animales(5, CategoriaAnimal.OVINO, "Merino", 2));

        boolean result = service.eliminarAnimalPorCaravana(5);

        assertTrue(result);
        assertEquals(0, service.listarAnimales().size());
    }

    @Test
    @Tag("regression")

    @DisplayName("eliminarAnimalPorCaravana(): debe devolver FALSE si el animal no existe")
    void eliminarAnimalPorCaravana_devuelveFalseSiNoExiste() {

        boolean result = service.eliminarAnimalPorCaravana(999);

        assertFalse(result);
    }

    @Test

    @DisplayName("listarAnimales(): debe devolver una lista independiente")
    void listarAnimales_retornaCopiaIndependiente() {

        service.agregarAnimal(new Animales(1, CategoriaAnimal.VACUNO, "A", 1));

        List<Animales> copia = service.listarAnimales();
        copia.clear();

        assertEquals(1, service.listarAnimales().size());
    }

    // ============================================================
    // MOVIMIENTOS
    // ============================================================

    @Test
    @Tag("smoke")
    @Tag("regression")

    @DisplayName("registrarMovimiento(): debe permitir un INGRESO sin validar existencia del animal")
    void registrarMovimiento_ingresoSinAnimal_loAgregaEnLaLista() {

        int sizeInicial = service.listarMovimientos().size();

        Movimientos mov = new Movimientos(100, TipoMovimiento.INGRESO, "Compra", "01/01/2025");

        assertDoesNotThrow(() -> service.registrarMovimiento(mov));
        assertEquals(sizeInicial + 1, service.listarMovimientos().size());
    }

    @Test
    @Tag("smoke")
    @Tag("regression")

    @DisplayName("registrarMovimiento(): SALIDA debe permitirse si el animal existe")
    void registrarMovimiento_salidaConAnimalExistente_funciona() {

        int initialSize = service.listarMovimientos().size();

        service.agregarAnimal(new Animales(20, CategoriaAnimal.VACUNO, "Hereford", 2));

        Movimientos mov = new Movimientos(20, TipoMovimiento.SALIDA, "Venta", "02/02/2025");

        assertDoesNotThrow(() -> service.registrarMovimiento(mov));
        assertEquals(initialSize + 1, service.listarMovimientos().size());
    }

    @Test
    @Tag("regression")

    @DisplayName("registrarMovimiento(): SALIDA debe lanzar error si el animal NO existe")
    void registrarMovimiento_salidaSinAnimal_lanzaError() {

        Movimientos mov = new Movimientos(999, TipoMovimiento.SALIDA, "Error", "10/10/2025");

        Exception e = assertThrows(IllegalArgumentException.class, () -> service.registrarMovimiento(mov));

        assertTrue(e.getMessage().contains("does not exist"));
    }

    @Test

    @DisplayName("listarMovimientos(): debe retornar una copia independiente")
    void listarMovimientos_retornaCopiaIndependiente() {

        int sizeInicial = service.listarMovimientos().size();

        service.registrarMovimiento(new Movimientos(1, TipoMovimiento.INGRESO, "Destino", "10/10/2025"));

        List<Movimientos> copia = service.listarMovimientos();
        copia.clear();

        assertEquals(sizeInicial + 1, service.listarMovimientos().size());
    }

    // ============================================================
    // SANIDAD
    // ============================================================

    @Test
    @Tag("smoke")
    @Tag("regression")

    @DisplayName("registrarSanidad(): debe guardar correctamente el registro")
    void registrarSanidad_guardaCorrectamente() {

        int sizeInicial = service.listarSanidad().size();

        Sanidad s = new Sanidad(10, "Aftosa", "01/03/2025");
        service.registrarSanidad(s);

        List<Sanidad> lista = service.listarSanidad();

        assertAll(() -> assertEquals(sizeInicial + 1, lista.size()),
                () -> assertEquals(s.getVacuna(), lista.get(lista.size() - 1).getVacuna()));
    }

    @Test

    @DisplayName("listarSanidad(): debe retornar una copia independiente")
    void listarSanidad_retornaCopiaIndependiente() {

        int initialSize = service.listarSanidad().size();

        service.registrarSanidad(new Sanidad(1, "Vacuna X", "01/01/2025"));

        List<Sanidad> copia = service.listarSanidad();
        copia.clear();

        assertEquals(initialSize + 1, service.listarSanidad().size());
    }
}
