package excusasHspring.modelo.empleados.encargados.evaluacion;

import modelo.empleados.encargados.Encargado;
import modelo.excusas.Excusa;

public interface IEvaluacionExcusa {
    void evaluar(Encargado encargado, Excusa excusa);
}
