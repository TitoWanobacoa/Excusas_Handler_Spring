package excusasHspring.modelo.tests.encargados;

import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.modelo.empleados.encargados.*;
import excusasHspring.modelo.empleados.encargados.evaluacion.EvaluacionNormal;
import excusasHspring.modelo.excusas.ITipoExcusa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import excusasHspring.servicios.AdministradorProntuario;
import excusasHspring.servicios.EmailSenderFake;
import excusasHspring.servicios.IAdministradorProntuario;
import excusasHspring.servicios.IEmailSender;


class TipoInvalido implements ITipoExcusa {
    @Override
    public boolean puedeSerAtendidaPor(Encargado encargado) {
        return false;
    }
}

class RechazoTest {

    private ManejadorDeExcusa manejador;
    private Empleado empleado;

    @BeforeEach
    void setUp() {
        IEmailSender emailSender = new EmailSenderFake();
        IAdministradorProntuario admin = AdministradorProntuario.getInstancia();


        Encargado recepcionista = new Recepcionista("Recepcionista", "recepcion@excusas.sa", 201, new EmailSenderFake());
        Encargado supervisor = new Supervisor("Supervisor", "supervision@excusas.sa", 202, new EmailSenderFake());
        Encargado gerente = new GerenteRRHH("Gerente RRHH", "rrhh@excusas.sa", 123, emailSender);
        Encargado ceo = new CEO("CEO", "ceo@excusas.sa", 999, emailSender, admin);
        Encargado rechazador = new RechazadorExcusas("Rechazador", "rechazo@excusas.sa", 203, new EmailSenderFake());


        recepcionista.setEstrategia(new EvaluacionNormal());
        supervisor.setEstrategia(new EvaluacionNormal());
        gerente.setEstrategia(new EvaluacionNormal());
        ceo.setEstrategia(new EvaluacionNormal());
        rechazador.setEstrategia(new EvaluacionNormal());

        manejador = new ManejadorDeExcusa(recepcionista, supervisor, gerente, ceo, rechazador);

        empleado = new Empleado("Lucía", "lucia@test.com", 456);
    }

    @Test
    void testExcusaRechazadaCuandoNadieLaAcepta() {
        ITipoExcusa excusaNoValida = new TipoInvalido();
        empleado.excusarse("Excusa no aceptada por nadie", excusaNoValida, manejador);


    }
}
