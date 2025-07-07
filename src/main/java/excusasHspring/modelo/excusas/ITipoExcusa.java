package excusasHspring.modelo.excusas;

import modelo.empleados.encargados.Encargado;

public interface ITipoExcusa {
    boolean puedeSerAtendidaPor(Encargado encargado);
}