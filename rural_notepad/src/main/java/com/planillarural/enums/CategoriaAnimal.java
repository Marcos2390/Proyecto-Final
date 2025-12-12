package com.planillarural.enums;

public enum CategoriaAnimal {

    // PRINCIPALES CATEGORIAS
    VACUNO(null),
    OVINO(null),
    CAPRINO(null),
    EQUINO(null),
    PORCINO(null),
    OTRO(null),

    // SUBCATEGORIAS DE VACUNOS
    TERNERO(VACUNO),
    TORO(VACUNO),
    NOVILLO(VACUNO),
    VAQUILLONA(VACUNO),
    VACA(VACUNO),

    // SUBCATEGORIA DE OVINOS
    CORDERO(OVINO),
    OVEJA(OVINO),
    CARNERO(OVINO);

    // Campo para subcategoría
    private final CategoriaAnimal parent;

    // Constructor
    CategoriaAnimal(CategoriaAnimal parent) {
        this.parent = parent;
    }

    // Saber si es subcategoría
    public boolean isSubcategoria() {
        return parent != null;
    }

    // Obtener categoría padre
    public CategoriaAnimal getParent() {
        return parent;
    }

    // Obtener la especie raíz (VACUNO, OVINO, etc.)
    public CategoriaAnimal getEspecieRaiz() {
        return isSubcategoria() ? parent : this;
    }

    // Convertir String a enum
    public static CategoriaAnimal fromString(String s) { // convierte el dato ingresado por el usuario en enum
        if (s == null || s.isBlank())
            return OTRO;
        try {
            return CategoriaAnimal.valueOf(s.trim().toUpperCase()); // convierte a mayusculas y quita espacios busca el
                                                                    // enum con el dato exacto
        } catch (IllegalArgumentException e) {
            return OTRO;
        }
    }

    @Override
    public String toString() {
        if (isSubcategoria()) {
            return parent.name() + " - " + name();
        }
        return name();
    }
}
