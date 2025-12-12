package com.planillarural.model;

public class Sanidad {
    private int idAnimal;
    private String vacuna;
    private String fecha; // formato dd/mm/yyyy (simple)

    public Sanidad(int idAnimal, String vacuna, String fecha) {
        this.idAnimal = idAnimal;
        this.vacuna = vacuna == null ? "" : vacuna; // guarda vacio si es null o tal cual si se completa
        this.fecha = fecha == null ? "" : fecha;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public String getVacuna() {
        return vacuna;
    }

    public String getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return String.format("Sanidad{idAnimal=%d, vacuna=%s, fecha=%s}", idAnimal, vacuna, fecha);
    }
}
