package excusasHspring.modelo.excusas;

import com.fasterxml.jackson.annotation.JsonCreator;
import excusasHspring.modelo.empleados.encargados.Encargado;

public enum TipoExcusa implements ITipoExcusa {
    COMPLEJA,
    MODERADA,
    TRIVIAL,
    INVEROSIMIL;

    @Override
    public boolean puedeSerAtendidaPor(Encargado encargado) {
        return encargado.aceptaExcusa(this);
    }

    @JsonCreator
    public static TipoExcusa fromString(String value) {
        return TipoExcusa.valueOf(value.toUpperCase());
    }
}
