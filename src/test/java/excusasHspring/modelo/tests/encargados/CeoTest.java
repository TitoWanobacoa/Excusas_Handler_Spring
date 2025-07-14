package excusasHspring.modelo.tests.encargados;

import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.modelo.empleados.encargados.CEO;
import excusasHspring.modelo.empleados.encargados.evaluacion.EvaluacionProductiva;
import excusasHspring.modelo.excusas.Compleja;
import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.modelo.excusas.ITipoExcusa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import excusasHspring.servicios.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CeoTest {

    private CEOFake ceo;
    private EmailSenderFake emailSender;
    private Empleado empleado;
    private ITipoExcusa tipoExcusa;

    @BeforeEach
    void setUp() {
        emailSender = new EmailSenderFake();
        IAdministradorProntuario admin = new AdministradorProntuario(); // Instancia directa

        ceo = new CEOFake(emailSender, admin);
        ceo.setEstrategia(new EvaluacionProductiva(emailSender, admin));

        admin.agregarObservador(ceo);

        empleado = new Empleado("Martín", "martin@test.com", 101);
        tipoExcusa = new Compleja();
    }

    @Test
    void testCeoProcesaExcusaCompleja() {
        Excusa excusa = new Excusa(empleado, tipoExcusa, "Excusa de prueba compleja");
        ceo.manejarExcusa(excusa);

        assertEquals("martin@test.com", emailSender.getUltimoDestinatario());
        assertEquals("ceo@excusas.sa", emailSender.getUltimoRemitente());
        assertEquals("Aprobado", emailSender.getUltimoAsunto());
        assertEquals("Tu excusa ha sido aceptada por creatividad.", emailSender.getUltimoCuerpo());

        NotificacionExcusa notificacion = ceo.getUltimaNotificacion();
        assertNotNull(notificacion);
        assertEquals("Martín", notificacion.getExcusa().getEmpleado().getNombre());
        assertEquals(tipoExcusa, notificacion.getExcusa().getTipo());
    }

    static class CEOFake extends CEO {
        private NotificacionExcusa ultimaNotificacion;

        public CEOFake(IEmailSender emailSender, IAdministradorProntuario admin) {
            super("CEO Fake", "ceo@excusas.sa", 999, emailSender, admin);
        }


        @Override
        public void actualizar(NotificacionExcusa notificacion) {
            super.actualizar(notificacion);
            this.ultimaNotificacion = notificacion;
        }

        public NotificacionExcusa getUltimaNotificacion() {
            return ultimaNotificacion;
        }
    }
}

