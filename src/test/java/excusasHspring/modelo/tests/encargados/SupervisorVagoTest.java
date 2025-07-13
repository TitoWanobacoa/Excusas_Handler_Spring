package excusasHspring.modelo.tests.encargados;

import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.modelo.empleados.encargados.*;
import excusasHspring.modelo.empleados.encargados.evaluacion.EvaluacionNormal;
import excusasHspring.modelo.empleados.encargados.evaluacion.EvaluacionVaga;
import excusasHspring.modelo.excusas.ITipoExcusa;
import excusasHspring.modelo.excusas.Moderada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import excusasHspring.servicios.AdministradorProntuario;
import excusasHspring.servicios.EmailSenderFake;
import excusasHspring.servicios.IAdministradorProntuario;
import excusasHspring.servicios.IEmailSender;

class SupervisorVagoTest {

    private ManejadorDeExcusa manejador;
    private Empleado empleado;

    @BeforeEach
    void setUp() {
        IEmailSender emailSender = new EmailSenderFake();
        IAdministradorProntuario admin = AdministradorProntuario.getInstancia();

        Encargado supervisor = new Supervisor("Supervisor", "supervision@excusas.sa", 202, emailSender);
        Encargado ceo = new CEO("CEO", "ceo@excusas.sa", 999, emailSender, admin);
        Encargado rechazador = new RechazadorExcusas("Rechazador", "rechazo@excusas.sa", 203, new EmailSenderFake());



        supervisor.setEstrategia(new EvaluacionVaga());


        ceo.setEstrategia(new EvaluacionNormal());
        rechazador.setEstrategia(new EvaluacionNormal());

        manejador = new ManejadorDeExcusa(supervisor, ceo, rechazador);

        empleado = new Empleado("Ana", "ana@mail.com", 789);
    }

    @Test
    void testSupervisorVagoPasaExcusaYCeoLaProcesa() {
        ITipoExcusa excusaModerada = new Moderada();
        empleado.excusarse("Corte de luz", excusaModerada, manejador);


    }
}
