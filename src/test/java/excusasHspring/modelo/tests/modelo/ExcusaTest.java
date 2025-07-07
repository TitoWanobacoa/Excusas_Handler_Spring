package excusasHspring.modelo.tests.modelo;

import modelo.empleados.Empleado;
import modelo.excusas.Excusa;
import modelo.excusas.ITipoExcusa;
import modelo.excusas.Trivial;
import org.junit.jupiter.api.Test;

class ExcusaTest {

    @Test
    void testCrearExcusa() {
        Empleado empleado = new Empleado("Laura", "laura@mail.com", 1001);
        ITipoExcusa tipo = new Trivial();

        Excusa excusa = new Excusa(empleado, tipo, "Excusa trivial de test");


        assertEquals("Laura", excusa.getEmpleado().getNombre());
        assertEquals("laura@mail.com", excusa.getEmpleado().getEmail());
        assertEquals(1001, excusa.getEmpleado().getNroLegajo());
        assertEquals(tipo, excusa.getTipo());
    }
}
