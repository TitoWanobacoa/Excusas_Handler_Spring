package excusasHspring.modelo.excusas;

import modelo.empleados.encargados.Encargado;

public class Inverosimil implements ITipoExcusa {
    @Override
    public boolean puedeSerAtendidaPor(Encargado encargado) {
        return encargado.aceptaInverosimil();
    }
}
