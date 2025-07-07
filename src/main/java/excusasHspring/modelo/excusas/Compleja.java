package excusasHspring.modelo.excusas;

import modelo.empleados.encargados.Encargado;

public class Compleja implements ITipoExcusa {
    @Override
    public boolean puedeSerAtendidaPor(Encargado encargado) {
        return encargado.aceptaCompleja();
    }
}
