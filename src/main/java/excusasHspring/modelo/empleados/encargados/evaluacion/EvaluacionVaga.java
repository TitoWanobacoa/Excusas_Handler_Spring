package excusasHspring.modelo.empleados.encargados.evaluacion;

import excusasHspring.modelo.empleados.encargados.Encargado;
import excusasHspring.modelo.excusas.Excusa;

public class EvaluacionVaga implements IEvaluacionExcusa {
    @Override
    public void evaluar(Encargado encargado, Excusa excusa) {
        System.out.println("Evaluación vaga. El encargado no se toma el trabajo.");
        encargado.pasarAlSiguiente(excusa);
    }
    @Override
    public String getNombre() {
        return "VAGA";
    }

}
