package com.planillarural.model;

import com.planillarural.enums.CategoriaAnimal;

public class Animales {
    private int id; // internal id
    private int numerocaravana;
    private CategoriaAnimal categoria;
    private String raza;
    private int edad;

    private static int sequence = 1; // secuencia para nuevo animal ascendente

    // exepcion de numero de caravana invalido
    public Animales(int numerocaravana, CategoriaAnimal categoria, String raza, int edad) {
        if (numerocaravana <= 0)
            throw new IllegalArgumentException("Invalid caravan number");
        this.id = sequence++;
        this.numerocaravana = numerocaravana;
        this.categoria = categoria;
        this.raza = raza == null ? "" : raza;
        this.edad = Math.max(0, edad); // en caso de numero negativo devuelve 0
    }

    // getters
    public int getId() {
        return id;
    }

    public int getNumerocaravana() {
        return numerocaravana;
    }

    public CategoriaAnimal getCategoria() {
        return categoria;
    }

    public String getRaza() {
        return raza;
    }

    public int getEdad() {
        return edad;
    }

    @Override
    public String toString() {
        return String.format("Animal{id=%d, caravana=%d, categoria=%s, raza=%s, edad=%d}", // " % " dato que sera
                                                                                           // completado y "letra" tipo
                                                                                           // de dato (String o decimal)
                id, numerocaravana, categoria, raza, edad);
    }

    // comparacion para la identificacion unica por animal que no arroje
    // repeticiones

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Animales))
            return false;
        Animales other = (Animales) o;
        return this.numerocaravana == other.numerocaravana;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(numerocaravana);
    }
}
