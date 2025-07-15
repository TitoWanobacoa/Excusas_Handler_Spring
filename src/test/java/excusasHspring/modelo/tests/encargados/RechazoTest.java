package excusasHspring.modelo.tests.encargados;

import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.modelo.empleados.encargados.*;
import excusasHspring.modelo.empleados.encargados.evaluacion.IEvaluacionExcusa;
import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.modelo.excusas.TipoExcusa;
import excusasHspring.modelo.servicios.*;
import excusasHspring.repository.ProntuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class RechazoTest {

    private ManejadorDeExcusa manejador;
    private Empleado empleado;
    private RechazadorExcusas rechazador;

    @BeforeEach
    void setUp() {
        IEmailSender emailSender = new EmailSenderFake();

        ProntuarioRepository fakeRepo = Mockito.mock(ProntuarioRepository.class);
        IAdministradorProntuario admin = new AdministradorProntuario(fakeRepo);

        Encargado recepcionista = new Recepcionista("Recepcionista", "recepcion@excusas.sa", 201, emailSender);
        Encargado supervisor = new Supervisor("Supervisor", "supervision@excusas.sa", 202, emailSender);
        Encargado gerente = new GerenteRRHH("Gerente RRHH", "rrhh@excusas.sa", 123, emailSender);
        Encargado ceo = new CEO("CEO", "ceo@excusas.sa", 999, emailSender, admin);
        rechazador = new RechazadorExcusas("Rechazador", "rechazo@excusas.sa", 203, emailSender);

        IEvaluacionExcusa estrategiaRechazo = new IEvaluacionExcusa() {
            @Override
            public boolean evaluar(Encargado encargado, Excusa excusa) {
                encargado.pasarAlSiguiente(excusa);
                return false;
            }
            @Override public String getNombre() { return "RECHAZO"; }
            @Override public String mensaje() { return "Nadie la acepta"; }
        };

        recepcionista.setEstrategia(estrategiaRechazo);
        supervisor.setEstrategia(estrategiaRechazo);
        gerente.setEstrategia(estrategiaRechazo);
        ceo.setEstrategia(estrategiaRechazo);
        rechazador.setEstrategia(estrategiaRechazo);

        manejador = new ManejadorDeExcusa(recepcionista, supervisor, gerente, ceo, rechazador);
        empleado = new Empleado("Lucía", "lucia@test.com", 456);
    }

    @Test
    void testExcusaRechazadaCuandoNadieLaAcepta() {
        empleado.excusarse("Excusa no aceptada por nadie", TipoExcusa.TRIVIAL, manejador);
        Excusa excusa = rechazador.getUltimaExcusaRechazada();
        assertNotNull(excusa);
        assertEquals("Excusa no aceptada por nadie", excusa.getDescripcion());
    }
}
