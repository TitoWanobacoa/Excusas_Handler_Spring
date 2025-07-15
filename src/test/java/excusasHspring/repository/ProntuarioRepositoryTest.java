package excusasHspring.repository;

import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.modelo.excusas.TipoExcusa;
import excusasHspring.modelo.servicios.Prontuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ProntuarioRepositoryTest {

    @Autowired
    private ProntuarioRepository prontuarioRepository;

    @Test
    void testGuardarYRecuperarProntuario() {
        Empleado empleado = new Empleado("Sofía", "sofia@mail.com", 321);
        Excusa excusa = new Excusa(empleado, TipoExcusa.INVEROSIMIL, "Me abdujeron los aliens");
        Prontuario prontuario = new Prontuario(excusa, "Se registró excusa inusual");

        prontuarioRepository.save(prontuario);

        List<Prontuario> lista = prontuarioRepository.findAll();
        assertFalse(lista.isEmpty());
        assertEquals("Se registró excusa inusual", lista.get(0).getMensaje());
        assertEquals("Me abdujeron los aliens", lista.get(0).getExcusa().getDescripcion());
    }
}
