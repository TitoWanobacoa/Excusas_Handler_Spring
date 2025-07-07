package excusasHspring.modelo.empleados.encargados;

import modelo.excusas.Excusa;
import servicios.IAdministradorProntuario;
import servicios.IEmailSender;
import servicios.IObservador;
import servicios.NotificacionExcusa;

public class CEO extends Encargado implements IObservador {
    private final IEmailSender emailSender;
    private final IAdministradorProntuario admin;

    public CEO(String nombre, String email, int legajo, IEmailSender emailSender, IAdministradorProntuario admin) {
        super(nombre, email, legajo);
        this.emailSender = emailSender;
        this.admin = admin;
    }

    @Override
    public boolean aceptaCompleja() {
        return true;
    }

    @Override
    public void procesar(Excusa excusa) {
        admin.guardarProntuario(excusa);
        emailSender.enviarEmail(
                excusa.getEmpleado().getEmail(),
                this.getEmail(),
                "Aprobado",
                "Tu excusa ha sido aceptada por creatividad."
        );
    }

    @Override
    public void actualizar(NotificacionExcusa notificacion) {
        System.out.println("Notificación recibida por el CEO:");
        if (notificacion.getEncargado() != null) {
            System.out.println("- Encargado: " + notificacion.getEncargado().getNombre());
        }
        System.out.println("- Excusa: " + notificacion.getExcusa().getDescripcion());
    }
}
