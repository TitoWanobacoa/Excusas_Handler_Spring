package excusasHspring.modelo.empleados.encargados.evaluacion;

import excusasHspring.modelo.empleados.encargados.Encargado;
import excusasHspring.modelo.excusas.Excusa;

public interface IEvaluacionExcusa {
    boolean evaluar(Encargado encargado, Excusa excusa);
    String getNombre();

    String mensaje();
}
