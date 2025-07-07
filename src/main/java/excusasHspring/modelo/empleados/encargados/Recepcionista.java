package excusasHspring.modelo.empleados.encargados;

import modelo.excusas.Excusa;
import servicios.IEmailSender;

public class Recepcionista extends Encargado {
    private final IEmailSender emailSender;

    public Recepcionista(String nombre, String email, int legajo, IEmailSender emailSender) {
        super(nombre, email, legajo);
        this.emailSender = emailSender;
    }

    @Override
    public boolean aceptaTrivial() {
        return true;
    }

    @Override
    public void procesar(Excusa excusa) {
        emailSender.enviarEmail(
                excusa.getEmpleado().getEmail(),
                "recepcion@excusas.sa",
                "Recibida",
                "Tu excusa fue recibida por la recepción. Estamos evaluando."
        );
    }
}
