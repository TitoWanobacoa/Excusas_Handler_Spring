package excusasHspring.modelo.tests.servicios;

import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.modelo.excusas.TipoExcusa;
import excusasHspring.modelo.servicios.AdministradorProntuario;
import excusasHspring.modelo.servicios.IObservador;
import excusasHspring.modelo.servicios.NotificacionExcusa;
import excusasHspring.repository.ProntuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class AdministradorProntuarioTest {

    private AdministradorProntuario admin;
    private TestObservador observador;
    private Empleado empleado;

    private static class TestObservador implements IObservador {
        Excusa excusaRecibida = null;

        @Override
        public void actualizar(NotificacionExcusa notificacion) {
            this.excusaRecibida = notificacion.getExcusa();
        }
    }

    @BeforeEach
    void setUp() {
        ProntuarioRepository mockRepo = mock(ProntuarioRepository.class);
        admin = new AdministradorProntuario(mockRepo);

        observador = new TestObservador();
        admin.agregarObservador(observador);

        empleado = new Empleado("Pablo", "pablo@test.com", 707);
    }

    @Test
    void testNotificaObservadoresAlGuardarExcusa() {
        Excusa excusa = new Excusa(empleado, TipoExcusa.TRIVIAL, "Excusa usada para guardar en prontuario");
        admin.guardarProntuario(excusa);

        assertNotNull(observador.excusaRecibida);
        assertEquals("Excusa usada para guardar en prontuario", observador.excusaRecibida.getDescripcion());
        assertEquals(excusa, observador.excusaRecibida);
    }
}
