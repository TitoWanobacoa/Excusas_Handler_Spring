package excusasHspring.modelo.empleados.encargados.evaluacion;

import excusasHspring.modelo.empleados.encargados.Encargado ;
import excusasHspring.modelo.excusas.Excusa;

public class EvaluacionNormal implements IEvaluacionExcusa {
    @Override
    public void evaluar(Encargado encargado, Excusa excusa) {
        if (excusa.getTipo().puedeSerAtendidaPor(encargado)) {
            encargado.procesar(excusa);
        } else {
            encargado.pasarAlSiguiente(excusa);
        }
    }
}
