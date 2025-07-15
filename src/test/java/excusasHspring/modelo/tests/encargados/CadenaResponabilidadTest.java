package excusasHspring.modelo.tests.encargados;

import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.modelo.empleados.encargados.*;
import excusasHspring.modelo.empleados.encargados.evaluacion.EvaluacionNormal;
import excusasHspring.modelo.empleados.encargados.evaluacion.EvaluacionProductiva;
import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.modelo.excusas.TipoExcusa;
import excusasHspring.modelo.servicios.*;
import excusasHspring.repository.ProntuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CadenaResponsabilidadTest {

    private ManejadorDeExcusa manejador;
    private Empleado empleadoTrivial;
    private Empleado empleadoModerado;
    private Empleado empleadoInverosimil;
    private Empleado empleadoComplejo;
    private Empleado empleadoNoValido;

    @BeforeEach
    void setUp() {
        IEmailSender emailSender = new EmailSenderFake();

        ProntuarioRepository fakeRepo = mock(ProntuarioRepository.class);
        when(fakeRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        IAdministradorProntuario admin = new AdministradorProntuario(fakeRepo);

        Encargado recepcionista = new Recepcionista("Recepcionista", "recepcion@excusas.sa", 201, emailSender);
        Encargado supervisor = new Supervisor("Supervisor", "supervision@excusas.sa", 202, emailSender);
        Encargado gerente = new GerenteRRHH("Gerente RRHH", "rrhh@excusas.sa", 123, emailSender);
        Encargado ceo = new CEO("CEO", "ceo@excusas.sa", 999, emailSender, admin);
        Encargado rechazador = new RechazadorExcusas("Rechazador", "rechazo@excusas.sa", 203, emailSender);

        recepcionista.setEstrategia(new EvaluacionNormal());
        supervisor.setEstrategia(new EvaluacionNormal());
        gerente.setEstrategia(new EvaluacionNormal());
        ceo.setEstrategia(new EvaluacionProductiva(emailSender, admin));
        rechazador.setEstrategia(new EvaluacionNormal());

        manejador = new ManejadorDeExcusa(recepcionista, supervisor, gerente, ceo, rechazador);

        empleadoTrivial = new Empleado("Ana", "ana@mail.com", 1);
        empleadoModerado = new Empleado("Luis", "luis@mail.com", 2);
        empleadoInverosimil = new Empleado("Nora", "nora@mail.com", 3);
        empleadoComplejo = new Empleado("Raúl", "raul@mail.com", 4);
        empleadoNoValido = new Empleado("Extra", "extra@mail.com", 5);
    }

    @Test
    void testCadenaCompletaConDistintosTipos() {
        empleadoTrivial.excusarse("Me quedé dormida", TipoExcusa.TRIVIAL, manejador);
        empleadoModerado.excusarse("Cuidé a un familiar", TipoExcusa.MODERADA, manejador);
        empleadoInverosimil.excusarse("Una paloma robó mi bici", TipoExcusa.INVEROSIMIL, manejador);
        empleadoComplejo.excusarse("Fui abducido por aliens", TipoExcusa.COMPLEJA, manejador);


        empleadoNoValido.excusarse("No tenía ganas", TipoExcusa.TRIVIAL, manejador);
    }
}
