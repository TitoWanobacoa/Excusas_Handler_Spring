package excusasHspring.modelo.empleados.encargados;

import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.modelo.servicios.IEmailSender;

public class Supervisor extends Encargado {
    private final IEmailSender emailSender;

    public Supervisor(String nombre, String email, int legajo, IEmailSender emailSender) {
        super(nombre, email, legajo);
        this.emailSender = emailSender;
    }

    @Override
    public boolean aceptaModerada() {
        return true;
    }

    @Override
    public void procesar(Excusa excusa) {
        emailSender.enviarEmail(
                excusa.getEmpleado().getEmail(),
                "supervision@excusas.sa",
                "Evaluación en curso",
                "Tu excusa está siendo evaluada por el supervisor."
        );
    }
}
