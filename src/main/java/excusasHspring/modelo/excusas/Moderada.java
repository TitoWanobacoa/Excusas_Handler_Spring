package excusasHspring.modelo.excusas;

import modelo.empleados.encargados.Encargado;

public class Moderada implements ITipoExcusa {
    @Override
    public boolean puedeSerAtendidaPor(Encargado encargado) {
        return encargado.aceptaModerada();
    }
}
