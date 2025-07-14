package excusasHspring.modelo.tests.encargados;

import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.modelo.empleados.encargados.*;
import excusasHspring.modelo.empleados.encargados.evaluacion.EvaluacionNormal;
import excusasHspring.modelo.empleados.encargados.evaluacion.EvaluacionProductiva;
import excusasHspring.modelo.excusas.*;
import excusasHspring.servicios.IAdministradorProntuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import excusasHspring.servicios.AdministradorProntuario;
import excusasHspring.servicios.EmailSenderFake;
import excusasHspring.servicios.IEmailSender;

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
        IAdministradorProntuario admin = new AdministradorProntuario();

        // Encargados
        Encargado recepcionista = new Recepcionista("Recepcionista", "recepcion@excusas.sa", 201, new EmailSenderFake());
        Encargado supervisor = new Supervisor("Supervisor", "supervision@excusas.sa", 202, new EmailSenderFake());
        Encargado gerente = new GerenteRRHH("Gerente RRHH", "rrhh@excusas.sa", 123, emailSender);
        Encargado ceo = new CEO("CEO", "ceo@excusas.sa", 999, emailSender, admin);
        Encargado rechazador = new RechazadorExcusas("Rechazador", "rechazo@excusas.sa", 203, new EmailSenderFake());

        // Estrategias
        recepcionista.setEstrategia(new EvaluacionNormal());
        supervisor.setEstrategia(new EvaluacionNormal());
        gerente.setEstrategia(new EvaluacionNormal());
        ceo.setEstrategia(new EvaluacionProductiva(emailSender, admin));
        rechazador.setEstrategia(new EvaluacionNormal());

        manejador = new ManejadorDeExcusa(recepcionista, supervisor, gerente, ceo, rechazador);

        // Empleados
        empleadoTrivial = new Empleado("Ana", "ana@mail.com", 1);
        empleadoModerado = new Empleado("Luis", "luis@mail.com", 2);
        empleadoInverosimil = new Empleado("Nora", "nora@mail.com", 3);
        empleadoComplejo = new Empleado("Raúl", "raul@mail.com", 4);
        empleadoNoValido = new Empleado("Extra", "extra@mail.com", 5);
    }

    @Test
    void testCadenaCompletaConDistintosTipos() {
        // Trivial a Recepcionista
        empleadoTrivial.excusarse("Me quedé dormida", new Trivial(), manejador);

        // Moderada a Supervisor
        empleadoModerado.excusarse("Cuidé a un familiar", new Moderada(), manejador);

        // Inverosímil a Gerente
        empleadoInverosimil.excusarse("Una paloma robó mi bici", new Inverosimil(), manejador);

        // Compleja a CEO
        empleadoComplejo.excusarse("Fui abducido por aliens", new Compleja(), manejador);

        // No aceptada por nadie, a Rechazador
        ITipoExcusa tipoFalso = new ITipoExcusa() {
            @Override
            public boolean puedeSerAtendidaPor(Encargado encargado) {
                return false;
            }
        };
        empleadoNoValido.excusarse("No tenía ganas", tipoFalso, manejador);

    }
}
