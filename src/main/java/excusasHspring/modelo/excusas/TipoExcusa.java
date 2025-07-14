package excusasHspring.modelo.excusas;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoExcusa {
    COMPLEJA,
    MODERADA,
    TRIVIAL,
    INVEROSIMIL;

    @JsonCreator
    public static TipoExcusa fromString(String value) {
        return TipoExcusa.valueOf(value.toUpperCase());
    }
}
