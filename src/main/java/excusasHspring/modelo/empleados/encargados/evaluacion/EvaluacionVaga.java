package excusasHspring.modelo.empleados.encargados.evaluacion;

import excusasHspring.modelo.empleados.encargados.Encargado;
import excusasHspring.modelo.excusas.Excusa;

public class EvaluacionVaga implements IEvaluacionExcusa {

    @Override
    public boolean evaluar(Encargado encargado, Excusa excusa) {
        System.out.println("Evaluación vaga: el encargado no se toma el trabajo de evaluar.");
        encargado.pasarAlSiguiente(excusa);
        return false;
    }

    @Override
    public String getNombre() {
        return "VAGA";
    }

    @Override
    public String mensaje() {
        return "Evaluación vaga aplicada (sin análisis)";
    }
}
