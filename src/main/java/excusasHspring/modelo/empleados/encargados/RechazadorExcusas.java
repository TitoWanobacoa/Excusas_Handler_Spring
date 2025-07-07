package excusasHspring.modelo.empleados.encargados;

import modelo.excusas.Excusa;
import servicios.IEmailSender;

public class RechazadorExcusas extends Encargado {
    private final IEmailSender emailSender;

    public RechazadorExcusas(String nombre, String email, int legajo, IEmailSender emailSender) {
        super(nombre, email, legajo);
        this.emailSender = emailSender;
    }

    @Override
    public void procesar(Excusa excusa) {
        emailSender.enviarEmail(
                excusa.getEmpleado().getEmail(),
                "rechazo@excusas.sa",
                "Excusa rechazada",
                "Lo sentimos, tu excusa fue rechazada."
        );
    }
}
