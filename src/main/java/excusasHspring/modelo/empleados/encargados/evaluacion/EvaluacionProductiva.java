package excusasHspring.modelo.empleados.encargados.evaluacion;

import excusasHspring.modelo.empleados.encargados.Encargado;
import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.servicios.IAdministradorProntuario;
import excusasHspring.servicios.IEmailSender;

public class EvaluacionProductiva implements IEvaluacionExcusa {

    private final IEmailSender emailSender;
    private final IAdministradorProntuario admin;

    public EvaluacionProductiva(IEmailSender emailSender, IAdministradorProntuario admin) {
        this.emailSender = emailSender;
        this.admin = admin;
    }

    @Override
    public void evaluar(Encargado encargado, Excusa excusa) {
        if (excusa.getTipo().puedeSerAtendidaPor(encargado)) {
            admin.guardarProntuario(excusa);
            encargado.aceptarExcusa(excusa); // registra al encargado que aceptó
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
    @Override
    public String getNombre() {
        return "PRODUCTIVA";
    }
}
