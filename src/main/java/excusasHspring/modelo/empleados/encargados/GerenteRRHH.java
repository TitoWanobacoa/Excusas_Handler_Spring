package excusasHspring.modelo.empleados.encargados;

import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.servicios.IEmailSender;

public class GerenteRRHH extends Encargado {
    private final IEmailSender emailSender;

    public GerenteRRHH(String nombre, String email, int legajo, IEmailSender emailSender) {
        super(nombre, email, legajo);
        this.emailSender = emailSender;
    }

    @Override
    public boolean aceptaInverosimil() {
        return true;
    }

    @Override
    public void procesar(Excusa excusa) {
        emailSender.enviarEmail(
                excusa.getEmpleado().getEmail(),
                "gerente@excusas.sa",
                "Excusa aceptada",
                "La excusa fue considerada válida."
        );
    }
}

