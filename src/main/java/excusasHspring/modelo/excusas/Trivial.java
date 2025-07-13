package excusasHspring.modelo.excusas;

import excusasHspring.modelo.empleados.encargados.Encargado;

public class Trivial implements ITipoExcusa {
    @Override
    public boolean puedeSerAtendidaPor(Encargado encargado) {
        return encargado.aceptaTrivial();
    }
}
