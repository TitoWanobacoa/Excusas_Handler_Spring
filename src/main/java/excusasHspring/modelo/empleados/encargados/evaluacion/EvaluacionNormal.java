package excusasHspring.modelo.empleados.encargados.evaluacion;

import excusasHspring.modelo.empleados.encargados.Encargado;
import excusasHspring.modelo.excusas.Excusa;

public class EvaluacionNormal implements IEvaluacionExcusa {

    @Override
    public boolean evaluar(Encargado encargado, Excusa excusa) {
        if (excusa.getTipo().puedeSerAtendidaPor(encargado)) {
            encargado.aceptarExcusa(excusa);
        } else {
            encargado.pasarAlSiguiente(excusa);
        }
        return false;
    }

    @Override
    public String getNombre() {
        return "NORMAL";
    }

    @Override
    public String mensaje() {
        return "Evaluación normal aplicada";
    }
}
