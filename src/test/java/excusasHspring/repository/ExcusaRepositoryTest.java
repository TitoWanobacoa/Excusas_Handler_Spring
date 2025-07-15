package excusasHspring.repository;

import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.modelo.excusas.TipoExcusa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ExcusaRepositoryTest {

    @Autowired
    private ExcusaRepository excusaRepository;

    @Test
    void testGuardarYBuscarExcusaPorLegajo() {
        Empleado empleado = new Empleado("Luis", "luis@mail.com", 789);
        Excusa excusa = new Excusa(empleado, TipoExcusa.TRIVIAL, "Olvidé la laptop");
        excusaRepository.save(excusa);

        List<Excusa> excusas = excusaRepository.findByEmpleadoNroLegajo(789);
        assertFalse(excusas.isEmpty());
        assertEquals("Olvidé la laptop", excusas.get(0).getDescripcion());
    }
}
