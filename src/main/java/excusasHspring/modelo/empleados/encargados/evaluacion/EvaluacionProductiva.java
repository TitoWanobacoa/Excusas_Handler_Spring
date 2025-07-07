package excusasHspring.modelo.empleados.encargados.evaluacion;

import modelo.empleados.encargados.Encargado;
import modelo.excusas.Excusa;
import servicios.AdministradorProntuario;
import servicios.IEmailSender;

public class EvaluacionProductiva implements IEvaluacionExcusa {
    private final IEmailSender emailSender;

    public EvaluacionProductiva(IEmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void evaluar(Encargado encargado, Excusa excusa) {
        if (excusa.getTipo().puedeSerAtendidaPor(encargado)) {
            AdministradorProntuario.getInstancia().guardarProntuario(excusa);
            encargado.procesar(excusa);
        } else {
            emailSender.enviarEmail(
                    "cto@excusas.sa",
                    "notificador-productivo@excusas.sa",
                    "Encargado productivo derivando excusa",
                    "Un encargado derivó una excusa no apta para él."
            );
            encargado.pasarAlSiguiente(excusa);
        }
    }
}
