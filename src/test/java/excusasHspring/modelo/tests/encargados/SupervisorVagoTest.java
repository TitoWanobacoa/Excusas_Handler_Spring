package excusasHspring.modelo.tests.encargados;

import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.modelo.empleados.encargados.*;
import excusasHspring.modelo.empleados.encargados.evaluacion.EvaluacionNormal;
import excusasHspring.modelo.empleados.encargados.evaluacion.EvaluacionVaga;
import excusasHspring.modelo.excusas.TipoExcusa;
import excusasHspring.modelo.servicios.AdministradorProntuario;
import excusasHspring.modelo.servicios.EmailSenderFake;
import excusasHspring.modelo.servicios.IAdministradorProntuario;
import excusasHspring.modelo.servicios.IEmailSender;
import excusasHspring.repository.ProntuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class SupervisorVagoTest {

    private ManejadorDeExcusa manejador;
    private Empleado empleado;

    @BeforeEach
    void setUp() {
        IEmailSender emailSender = new EmailSenderFake();
        ProntuarioRepository mockRepo = mock(ProntuarioRepository.class);
        IAdministradorProntuario admin = new AdministradorProntuario(mockRepo);

        Encargado supervisor = new Supervisor("Supervisor", "supervision@excusas.sa", 202, emailSender);
        Encargado ceo = new CEO("CEO", "ceo@excusas.sa", 999, emailSender, admin);
        Encargado rechazador = new RechazadorExcusas("Rechazador", "rechazo@excusas.sa", 203, emailSender);

        supervisor.setEstrategia(new EvaluacionVaga());
        ceo.setEstrategia(new EvaluacionNormal());
        rechazador.setEstrategia(new EvaluacionNormal());

        manejador = new ManejadorDeExcusa(supervisor, ceo, rechazador);
        empleado = new Empleado("Ana", "ana@mail.com", 789);
    }

    @Test
    void testSupervisorVagoPasaExcusaYCeoLaProcesa() {
        empleado.excusarse("Corte de luz", TipoExcusa.MODERADA, manejador);
    }
}
