package com.planillarural.enums;

public enum MenuOption {
    AGREGAR_ANIMAL(1, "Add animal"),
    ELIMINAR_ANIMAL(2, "Remove animal"),
    REGISTRAR_VACUNA(3, "Register vaccine"),
    REGISTRAR_MOVIMIENTO(4, "Register movement"),
    LISTAR_ANIMALES(5, "List animals"),
    LISTAR_SANIDAD(6, "List health records"),
    LISTAR_MOVIMIENTOS(7, "List movements"),
    SEARCH_ANIMAL(8, "Search animal by caravan number"),
    EXIT(0, "Exit");

    private final int code;
    private final String description;

    MenuOption(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static MenuOption fromCode(int code) { // convierte el numero ingrsado por el usuario en enum
        for (MenuOption m : values()) { // recorre los enum, si no lo encuentra devuelve null
            if (m.code == code)
                return m;
        }
        return null;
    }
}