package com.planillarural.service;

import java.util.ArrayList;
import java.util.List;

import com.planillarural.enums.CategoriaAnimal;
import com.planillarural.enums.TipoMovimiento;
import com.planillarural.model.Animales;
import com.planillarural.model.Movimientos;
import com.planillarural.model.Sanidad;

public class RegistroService {

    private final List<Animales> animales = new ArrayList<>();
    private final List<Movimientos> movimientos = new ArrayList<>();
    private final List<Sanidad> sanidad = new ArrayList<>();

    // ======================
    // ANIMALES
    // ======================

    public void agregarAnimal(Animales animal) {
        Animales existente = buscarAnimalPorCaravanaYEspecie(
                animal.getNumerocaravana(),
                animal.getCategoria());

        if (existente != null) {
            throw new IllegalArgumentException(
                    "Animal with caravan number "
                            + animal.getNumerocaravana()
                            + " already exists in species "
                            + animal.getCategoria().getEspecieRaiz());
        }

        animales.add(animal);
    }

    public boolean eliminarAnimal(int caravana, CategoriaAnimal categoria) {
        return animales.removeIf(a -> a.getNumerocaravana() == caravana &&
                a.getCategoria() == categoria);
    }

    // Método requerido por tests
    public boolean eliminarAnimalPorCaravana(int caravana) {
        return animales.removeIf(a -> a.getNumerocaravana() == caravana);
    }

    // Método necesario para Main
    public Animales buscarAnimalPorCaravana(int caravana) {
        return animales.stream()
                .filter(a -> a.getNumerocaravana() == caravana)
                .findFirst()
                .orElse(null);
    }

    public Animales buscarAnimalPorCaravanaYEspecie(int caravana, CategoriaAnimal categoria) {
        return animales.stream()
                .filter(a -> a.getNumerocaravana() == caravana &&
                        a.getCategoria().getEspecieRaiz() == categoria.getEspecieRaiz())
                .findFirst()
                .orElse(null);
    }

    public List<Animales> buscarAnimalesPorCaravana(int caravana) {
        List<Animales> resultado = new ArrayList<>();
        for (Animales a : animales) {
            if (a.getNumerocaravana() == caravana) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    public List<Animales> listarAnimales() {
        return new ArrayList<>(animales);
    }

    // ======================
    // MOVIMIENTOS
    // ======================

    public void registrarMovimiento(Movimientos mov) {

        if (mov.getTipo() == TipoMovimiento.SALIDA) {
            if (buscarAnimalPorCaravana(mov.getIdAnimal()) == null) {
                throw new IllegalArgumentException(
                        "Cannot register EXIT. Animal with caravan number "
                                + mov.getIdAnimal()
                                + " does not exist.");
            }
        }

        movimientos.add(mov);
    }

    public List<Movimientos> listarMovimientos() {
        return new ArrayList<>(movimientos);
    }

    // ======================
    // SANIDAD
    // ======================

    public void registrarSanidad(Sanidad s) {
        sanidad.add(s);
    }

    public List<Sanidad> listarSanidad() {
        return new ArrayList<>(sanidad);
    }
}
