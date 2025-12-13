package com.planillarural.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
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

    private static final String PATH_ANIMALES = "data/animales.txt";
    private static final String PATH_MOVIMIENTOS = "data/movimientos.txt";
    private static final String PATH_SANIDAD = "data/sanidad.txt";

    public RegistroService(boolean cargarDesdeArchivo) {
        if (cargarDesdeArchivo) {
            cargarAnimales();
            cargarMovimientos();
            cargarSanidad();
        }
    }

    public RegistroService() {
        this(true); // por defecto carga desde archivo
    }

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
        guardarAnimal(animal);
    }

    public boolean eliminarAnimal(int caravana, CategoriaAnimal categoria) {
        return animales.removeIf(a -> a.getNumerocaravana() == caravana &&
                a.getCategoria() == categoria);
    }

    public boolean eliminarAnimalPorCaravana(int caravana) {
        return animales.removeIf(a -> a.getNumerocaravana() == caravana);
    }

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

    private void guardarAnimal(Animales a) {
        try {
            File dir = new File("data");
            if (!dir.exists())
                dir.mkdir();
            try (PrintWriter pw = new PrintWriter(new FileWriter(PATH_ANIMALES, true))) {
                pw.println(a.getId() + "," + a.getNumerocaravana() + "," + a.getCategoria() + "," + a.getRaza() + ","
                        + a.getEdad());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarAnimales() {
        File file = new File(PATH_ANIMALES);
        if (!file.exists())
            return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int caravana = Integer.parseInt(parts[1]);
                CategoriaAnimal cat = CategoriaAnimal.valueOf(parts[2]);
                String raza = parts[3];
                int edad = Integer.parseInt(parts[4]);
                Animales a = new Animales(caravana, cat, raza, edad);
                animales.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        guardarMovimiento(mov);
    }

    public List<Movimientos> listarMovimientos() {
        return new ArrayList<>(movimientos);
    }

    private void guardarMovimiento(Movimientos m) {
        try {
            File dir = new File("data");
            if (!dir.exists())
                dir.mkdir();
            try (PrintWriter pw = new PrintWriter(new FileWriter(PATH_MOVIMIENTOS, true))) {
                pw.println(m.getIdAnimal() + "," + m.getTipo() + "," + m.getDestino() + "," + m.getFecha());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarMovimientos() {
        File file = new File(PATH_MOVIMIENTOS);
        if (!file.exists())
            return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int idAnimal = Integer.parseInt(parts[0]);
                TipoMovimiento tipo = TipoMovimiento.valueOf(parts[1]);
                String desc = parts[2];
                String fecha = parts[3];
                movimientos.add(new Movimientos(idAnimal, tipo, desc, fecha));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ======================
    // SANIDAD
    // ======================

    public void registrarSanidad(Sanidad s) {
        sanidad.add(s);
        guardarSanidad(s);
    }

    public List<Sanidad> listarSanidad() {
        return new ArrayList<>(sanidad);
    }

    private void guardarSanidad(Sanidad s) {
        try {
            File dir = new File("data");
            if (!dir.exists())
                dir.mkdir();
            try (PrintWriter pw = new PrintWriter(new FileWriter(PATH_SANIDAD, true))) {
                pw.println(s.getIdAnimal() + "," + s.getVacuna() + "," + s.getFecha());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarSanidad() {
        File file = new File(PATH_SANIDAD);
        if (!file.exists())
            return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int idAnimal = Integer.parseInt(parts[0]);
                String vacuna = parts[1];
                String fecha = parts[2];
                sanidad.add(new Sanidad(idAnimal, vacuna, fecha));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 * package com.planillarural.service;
 * 
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 * import com.planillarural.enums.CategoriaAnimal;
 * import com.planillarural.enums.TipoMovimiento;
 * import com.planillarural.model.Animales;
 * import com.planillarural.model.Movimientos;
 * import com.planillarural.model.Sanidad;
 * 
 * public class RegistroService {
 * 
 * private final List<Animales> animales = new ArrayList<>();
 * private final List<Movimientos> movimientos = new ArrayList<>();
 * private final List<Sanidad> sanidad = new ArrayList<>();
 * 
 * // ======================
 * // ANIMALES
 * // ======================
 * 
 * public void agregarAnimal(Animales animal) {
 * Animales existente = buscarAnimalPorCaravanaYEspecie(
 * animal.getNumerocaravana(),
 * animal.getCategoria()); // busca si ya existe un animal con el mismo numero
 * de caravana y especie
 * 
 * if (existente != null) {
 * throw new IllegalArgumentException(
 * "Animal with caravan number "
 * + animal.getNumerocaravana()
 * + " already exists in species "
 * + animal.getCategoria().getEspecieRaiz()); // valida que no se repita el
 * numero de caravana
 * // en la misma especie
 * }
 * 
 * animales.add(animal);
 * }
 * 
 * public boolean eliminarAnimal(int caravana, CategoriaAnimal categoria) {
 * return animales.removeIf(a -> a.getNumerocaravana() == caravana &&
 * a.getCategoria() == categoria);
 * }
 * 
 * public boolean eliminarAnimalPorCaravana(int caravana) {
 * return animales.removeIf(a -> a.getNumerocaravana() == caravana);
 * }
 * 
 * // Método necesario para Main
 * public Animales buscarAnimalPorCaravana(int caravana) {
 * return animales.stream()
 * .filter(a -> a.getNumerocaravana() == caravana)
 * .findFirst()
 * .orElse(null); // ACA ES LA LOGICA SIMPLE DONDE BUSCA EL ANIMAL POR NUMERO DE
 * CARAVANA SOLO
 * }
 * 
 * public Animales buscarAnimalPorCaravanaYEspecie(int caravana, CategoriaAnimal
 * categoria) {
 * return animales.stream() //
 * .filter(a -> a.getNumerocaravana() == caravana && // ACA SE HACE UN FILTRO EN
 * DONDE DEVE COINCIDIR EL
 * // NUMERO DE CARAVANA Y LA ESPECIE PARA QUE SEA UN
 * // DUPLICADO
 * a.getCategoria().getEspecieRaiz() == categoria.getEspecieRaiz())
 * .findFirst()
 * .orElse(null);
 * }
 * 
 * public List<Animales> buscarAnimalesPorCaravana(int caravana) { // busca
 * todos los animales con el mismo numero de
 * // caravana
 * List<Animales> resultado = new ArrayList<>();
 * for (Animales a : animales) {
 * if (a.getNumerocaravana() == caravana) { // si el numero de caravana coincide
 * resultado.add(a); // lo agrega a la lista de resultados
 * }
 * }
 * return resultado;
 * }
 * 
 * public List<Animales> listarAnimales() {
 * return new ArrayList<>(animales);
 * }
 * 
 * // ======================
 * // MOVIMIENTOS
 * // ======================
 * 
 * public void registrarMovimiento(Movimientos mov) {
 * 
 * if (mov.getTipo() == TipoMovimiento.SALIDA) {
 * if (buscarAnimalPorCaravana(mov.getIdAnimal()) == null) { // valida que el
 * animal exista antes de registrar
 * // una salida
 * throw new IllegalArgumentException(
 * "Cannot register EXIT. Animal with caravan number "
 * + mov.getIdAnimal()
 * + " does not exist.");
 * }
 * }
 * 
 * movimientos.add(mov);
 * }
 * 
 * public List<Movimientos> listarMovimientos() {
 * return new ArrayList<>(movimientos);
 * }
 * 
 * // ======================
 * // SANIDAD
 * // ======================
 * 
 * public void registrarSanidad(Sanidad s) {
 * sanidad.add(s);
 * }
 * 
 * public List<Sanidad> listarSanidad() {
 * return new ArrayList<>(sanidad);
 * }
 * }/*
 */
