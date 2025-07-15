package excusasHspring.repository;

import excusasHspring.modelo.empleados.Empleado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class EmpleadoRepositoryTest {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Test
    void testGuardarYBuscarEmpleado() {
        Empleado empleado = new Empleado("Juan", "juan@mail.com", 123);
        empleadoRepository.save(empleado);

        Optional<Empleado> resultado = empleadoRepository.findByNroLegajo(123);
        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
    }
}
