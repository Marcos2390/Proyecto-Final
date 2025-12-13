package com.planillarural.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Sanidad {

    private int idAnimal;
    private String vacuna;
    private LocalDate fecha;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Sanidad(int idAnimal, String vacuna, String fecha) {

        if (idAnimal <= 0) {
            throw new IllegalArgumentException("Invalid animal id");
        }

        this.idAnimal = idAnimal;
        this.vacuna = vacuna == null ? "" : vacuna;

        try {
            this.fecha = LocalDate.parse(fecha, FORMATTER);
        } catch (DateTimeParseException | NullPointerException e) {
            throw new IllegalArgumentException(
                    "Invalid date format. Expected dd/MM/yyyy");
        }
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public String getVacuna() {
        return vacuna;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return String.format(
                "Sanidad{idAnimal=%d, vacuna=%s, fecha=%s}",
                idAnimal, vacuna, fecha.format(FORMATTER));
    }
}

/*
 * package com.planillarural.model;
 * 
 * 
 * 
 * public class Sanidad {
 * private int idAnimal;
 * private String vacuna;
 * private String fecha; // formato dd/mm/yyyy (simple)
 * 
 * public Sanidad(int idAnimal, String vacuna, String fecha) {
 * this.idAnimal = idAnimal;
 * this.vacuna = vacuna == null ? "" : vacuna; // guarda vacio si es null o tal
 * cual si se completa
 * this.fecha = fecha == null ? "" : fecha;
 * }
 * 
 * public int getIdAnimal() {
 * return idAnimal;
 * }
 * 
 * public String getVacuna() {
 * return vacuna;
 * }
 * 
 * public String getFecha() {
 * return fecha;
 * }
 * 
 * @Override
 * public String toString() {
 * return String.format("Sanidad{idAnimal=%d, vacuna=%s, fecha=%s}", idAnimal,
 * vacuna, fecha);
 * }
 * }/*
 */
