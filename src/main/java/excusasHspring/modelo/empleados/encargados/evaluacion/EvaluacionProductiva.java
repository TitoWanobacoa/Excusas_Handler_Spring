package excusasHspring.modelo.empleados.encargados.evaluacion;

import excusasHspring.modelo.empleados.encargados.Encargado;
import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.modelo.servicios.IAdministradorProntuario;
import excusasHspring.modelo.servicios.IEmailSender;

public class EvaluacionProductiva implements IEvaluacionExcusa {

    private final IEmailSender emailSender;
    private final IAdministradorProntuario admin;

    public EvaluacionProductiva(IEmailSender emailSender, IAdministradorProntuario admin) {
        this.emailSender = emailSender;
        this.admin = admin;
    }

    @Override
    public boolean evaluar(Encargado encargado, Excusa excusa) {
        if (encargado.aceptaExcusa(excusa.getTipo())) {
            admin.guardarProntuario(excusa);
            encargado.aceptarExcusa(excusa);
        } else {
            emailSender.enviarEmail(
                    "cto@excusas.sa",
                    "notificador-productivo@excusas.sa",
                    "Encargado productivo derivando excusa",
                    "Un encargado derivó una excusa no apta para él."
            );
            encargado.pasarAlSiguiente(excusa);
        }
        return false;
    }

    @Override
    public String getNombre() {
        return "PRODUCTIVA";
    }

    @Override
    public String mensaje() {
        return "Evaluación productiva realizada";
    }
}
