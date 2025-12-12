
package com.planillarural;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.planillarural.enums.CategoriaAnimal;
import com.planillarural.enums.TipoMovimiento;
import com.planillarural.model.Animales;
import com.planillarural.model.Movimientos;
import com.planillarural.model.Sanidad;
import com.planillarural.service.RegistroService;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final RegistroService registro = new RegistroService();

    public static void main(String[] args) {

        int option;

        do {
            System.out.println("=== SYSTEM ===");
            System.out.println("1 - Add animal");
            System.out.println("2 - Remove animal");
            System.out.println("3 - Register vaccine");
            System.out.println("4 - Register movement");
            System.out.println("5 - List animals");
            System.out.println("6 - List health records");
            System.out.println("7 - List movements");
            System.out.println("8 - Search animal by caravan number");
            System.out.println("0 - Exit");
            System.out.print("Option: ");
            option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 1 -> addAnimal();
                case 2 -> removeAnimal();
                case 3 -> registerVaccine();
                case 4 -> registerMovement();
                case 5 -> listAnimals();
                case 6 -> listSanidad();
                case 7 -> listMovements();
                case 8 -> searchAnimal();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option");
            }

            System.out.println();

        } while (option != 0); // finaliza el programa cuando el usuario ingresa 0
    }

    // ============================================
    // 1 - ADD ANIMAL
    // ============================================

    private static void addAnimal() {
        try {
            System.out.print("Caravan number: ");
            int caravana = Integer.parseInt(sc.nextLine());

            System.out.println("Choose category/subclass:");
            List<CategoriaAnimal> opciones = new ArrayList<>();
            int i = 1;

            for (CategoriaAnimal c : CategoriaAnimal.values()) {
                System.out.println(i + " - " + c);
                opciones.add(c);
                i++;
            }

            System.out.print("Option: ");
            int sel = Integer.parseInt(sc.nextLine());
            CategoriaAnimal categoria = opciones.get(sel - 1);

            System.out.print("Breed: ");
            String raza = sc.nextLine();

            System.out.print("Age: ");
            int edad = Integer.parseInt(sc.nextLine());

            registro.agregarAnimal(new Animales(caravana, categoria, raza, edad));
            System.out.println("Animal created.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ============================================
    // 2 - REMOVE ANIMAL
    // ============================================

    private static void removeAnimal() {
        try {
            System.out.print("Caravan number: ");
            int caravana = Integer.parseInt(sc.nextLine());

            System.out.println("Category:");
            List<CategoriaAnimal> opciones = new ArrayList<>();
            int i = 1;
            for (CategoriaAnimal c : CategoriaAnimal.values()) {
                System.out.println(i + " - " + c);
                opciones.add(c);
                i++;
            }

            System.out.print("Enter category number: ");
            int sel = Integer.parseInt(sc.nextLine());

            if (sel < 1 || sel > opciones.size()) {
                System.out.println("Invalid category.");
                return;
            }

            CategoriaAnimal categoria = opciones.get(sel - 1);

            boolean removed = registro.eliminarAnimal(caravana, categoria);

            if (removed)
                System.out.println("Animal removed.");
            else
                System.out.println("Animal not found with that caravan number and category.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ============================================
    // 3 - REGISTER VACCINE
    // ============================================

    private static void registerVaccine() {
        try {
            System.out.print("Caravan number: ");
            int caravana = Integer.parseInt(sc.nextLine());

            Animales a = registro.buscarAnimalPorCaravana(caravana);
            if (a == null)
                throw new IllegalArgumentException("Animal not found.");

            System.out.print("Vaccine name: ");
            String vacuna = sc.nextLine();

            System.out.print("Date (dd/mm/yyyy): ");
            String fecha = sc.nextLine();

            registro.registrarSanidad(new Sanidad(caravana, vacuna, fecha));
            System.out.println("Vaccine registered.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ============================================
    // 4 - REGISTER MOVEMENT
    // ============================================

    private static void registerMovement() {
        try {
            System.out.print("Caravan number: ");
            int caravana = Integer.parseInt(sc.nextLine());

            System.out.print("Type (INGRESO/SALIDA): ");
            TipoMovimiento tipo = TipoMovimiento.fromString(sc.nextLine());

            if (tipo == null)
                throw new IllegalArgumentException("Invalid type");

            Animales animal = registro.buscarAnimalPorCaravana(caravana);

            // INGRESO → crear animal si no existe
            if (tipo == TipoMovimiento.INGRESO && animal == null) {

                System.out.println("Animal does not exist. Creating new animal entry...");

                System.out.println("Choose category:");
                List<CategoriaAnimal> opciones = new ArrayList<>();
                int i = 1;
                for (CategoriaAnimal c : CategoriaAnimal.values()) {
                    System.out.println(i + " - " + c);
                    opciones.add(c);
                    i++;
                }

                System.out.print("Option: ");
                int sel = Integer.parseInt(sc.nextLine());
                CategoriaAnimal categoria = opciones.get(sel - 1);

                System.out.print("Breed: ");
                String raza = sc.nextLine();

                System.out.print("Age: ");
                int edad = Integer.parseInt(sc.nextLine());

                animal = new Animales(caravana, categoria, raza, edad);
                registro.agregarAnimal(animal);

                System.out.println("Animal created successfully!");
            }

            // SALIDA → error si no existe
            if (tipo == TipoMovimiento.SALIDA && animal == null)
                throw new IllegalArgumentException("Cannot register EXIT. Animal does not exist.");

            System.out.print("Destination: ");
            String dest = sc.nextLine();

            System.out.print("Date (dd/mm/yyyy): ");
            String fecha = sc.nextLine();

            registro.registrarMovimiento(new Movimientos(caravana, tipo, dest, fecha));
            System.out.println("Movement recorded.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ============================================
    // 5 - LIST ANIMALS
    // ============================================

    private static void listAnimals() {
        registro.listarAnimales().forEach(System.out::println);
    }

    // ============================================
    // 6 - LIST SANIDAD
    // ============================================

    private static void listSanidad() {
        registro.listarSanidad().forEach(System.out::println);
    }

    // ============================================
    // 7 - LIST MOVEMENTS
    // ============================================

    private static void listMovements() {
        registro.listarMovimientos().forEach(System.out::println);
    }

    // ============================================
    // 8 - SEARCH ANIMAL
    // ============================================

    private static void searchAnimal() {
        try {
            System.out.print("Caravan number: ");
            int num = Integer.parseInt(sc.nextLine());

            List<Animales> lista = registro.buscarAnimalesPorCaravana(num);

            if (lista.isEmpty()) {
                System.out.println("No animals found.");
            } else {
                lista.forEach(System.out::println);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}