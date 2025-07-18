package excusasHspring.modelo.tests.encargados;

import modelo.empleados.Empleado;
import modelo.empleados.encargados.CEO;
import modelo.empleados.encargados.evaluacion.EvaluacionProductiva;
import modelo.excusas.Compleja;
import modelo.excusas.Excusa;
import modelo.excusas.ITipoExcusa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicios.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CeoTest {

    private CEOFake ceo;
    private EmailSenderFake emailSender;
    private Empleado empleado;
    private ITipoExcusa tipoExcusa;

    @BeforeEach
    void setUp() {
        emailSender = new EmailSenderFake();
        ceo = new CEOFake(emailSender, AdministradorProntuario.getInstancia());
        ceo.setEstrategia(new EvaluacionProductiva(emailSender));

        AdministradorProntuario.getInstancia().agregarObservador(ceo);

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
            super("CEO Fake", "ceo@fake.com", 999, emailSender, admin);
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

