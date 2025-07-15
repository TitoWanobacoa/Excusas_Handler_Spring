package excusasHspring.repository;

import excusasHspring.modelo.empleados.encargados.Encargado;
import excusasHspring.modelo.excusas.Excusa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class EncargadoRepositoryTest {

    @Autowired
    private EncargadoRepository encargadoRepository;

    @Test
    void testGuardarYBuscarEncargadoPorLegajo() {
        Encargado encargado = new Encargado("Marta", "marta@mail.com", 456) {
            @Override
            public void procesar(Excusa excusa) {
            }
        };
        encargadoRepository.save(encargado);

        List<Encargado> encontrados = encargadoRepository.findAll();
        assertFalse(encontrados.isEmpty());
        assertEquals("Marta", encontrados.get(0).getNombre());
    }
}
