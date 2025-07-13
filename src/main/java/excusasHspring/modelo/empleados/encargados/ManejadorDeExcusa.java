package excusasHspring.modelo.empleados.encargados;

import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.modelo.excusas.ITipoExcusa ;

public class ManejadorDeExcusa {
    private final Encargado encargadoInicial;

    public ManejadorDeExcusa(Encargado... encargados) {
        for (int i = 0; i < encargados.length - 1; i++) {
            encargados[i].setSiguiente(encargados[i + 1]);
        }
        this.encargadoInicial = encargados[0];
    }

    public void recibirExcusa(String descripcion, Empleado empleado, ITipoExcusa tipo) {
        Excusa excusa = new Excusa(empleado, tipo, descripcion);
        encargadoInicial.manejarExcusa(excusa);
    }
}
