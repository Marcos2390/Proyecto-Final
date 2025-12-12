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
                animal.getCategoria()); // busca si ya existe un animal con el mismo numero de caravana y especie

        if (existente != null) {
            throw new IllegalArgumentException(
                    "Animal with caravan number "
                            + animal.getNumerocaravana()
                            + " already exists in species "
                            + animal.getCategoria().getEspecieRaiz()); // valida que no se repita el numero de caravana
                                                                       // en la misma especie
        }

        animales.add(animal);
    }

    public boolean eliminarAnimal(int caravana, CategoriaAnimal categoria) {
        return animales.removeIf(a -> a.getNumerocaravana() == caravana &&
                a.getCategoria() == categoria);
    }

    public boolean eliminarAnimalPorCaravana(int caravana) {
        return animales.removeIf(a -> a.getNumerocaravana() == caravana);
    }

    // Método necesario para Main
    public Animales buscarAnimalPorCaravana(int caravana) {
        return animales.stream()
                .filter(a -> a.getNumerocaravana() == caravana)
                .findFirst()
                .orElse(null); // ACA ES LA LOGICA SIMPLE DONDE BUSCA EL ANIMAL POR NUMERO DE CARAVANA SOLO
    }

    public Animales buscarAnimalPorCaravanaYEspecie(int caravana, CategoriaAnimal categoria) {
        return animales.stream() //
                .filter(a -> a.getNumerocaravana() == caravana && // ACA SE HACE UN FILTRO EN DONDE DEVE COINCIDIR EL
                                                                  // NUMERO DE CARAVANA Y LA ESPECIE PARA QUE SEA UN
                                                                  // DUPLICADO
                        a.getCategoria().getEspecieRaiz() == categoria.getEspecieRaiz())
                .findFirst()
                .orElse(null);
    }

    public List<Animales> buscarAnimalesPorCaravana(int caravana) { // busca todos los animales con el mismo numero de
                                                                    // caravana
        List<Animales> resultado = new ArrayList<>();
        for (Animales a : animales) {
            if (a.getNumerocaravana() == caravana) { // si el numero de caravana coincide
                resultado.add(a); // lo agrega a la lista de resultados
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
            if (buscarAnimalPorCaravana(mov.getIdAnimal()) == null) { // valida que el animal exista antes de registrar
                                                                      // una salida
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
