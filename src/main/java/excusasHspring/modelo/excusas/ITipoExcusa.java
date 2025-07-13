package excusasHspring.modelo.excusas;

import excusasHspring.modelo.empleados.encargados.Encargado;

public interface ITipoExcusa {
    boolean puedeSerAtendidaPor(Encargado encargado);
}