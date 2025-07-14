package excusasHspring.modelo.empleados.encargados.evaluacion;

import excusasHspring.servicios.IAdministradorProntuario;
import excusasHspring.servicios.IEmailSender;

public class EvaluacionFactory {

    public static IEvaluacionExcusa crearEstrategia(ModoEvaluacion modo, IEmailSender emailSender, IAdministradorProntuario admin) {
        return switch (modo) {
            case NORMAL -> new EvaluacionNormal();
            case VAGA -> new EvaluacionVaga();
            case PRODUCTIVA -> new EvaluacionProductiva(emailSender, admin);
        };
    }
}
