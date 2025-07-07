package excusasHspring.modelo.excusas;

import modelo.empleados.encargados.Encargado;

public class Trivial implements ITipoExcusa {
    @Override
    public boolean puedeSerAtendidaPor(Encargado encargado) {
        return encargado.aceptaTrivial();
    }
}
