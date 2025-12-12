package com.planillarural.enums;

public enum TipoMovimiento {
    INGRESO,
    SALIDA;

    public static TipoMovimiento fromString(String s) { // permite recibir cualquier texto y devolver un TipoMovimiento
                                                        // válido si coincide, o null si no es válido o está vacío.
        if (s == null || s.isBlank())
            return null;
        try {
            return TipoMovimiento.valueOf(s.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
