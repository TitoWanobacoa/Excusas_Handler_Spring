package excusasHspring.modelo.empleados;

import excusasHspring.modelo.empleados.encargados.ManejadorDeExcusa ;
import excusasHspring.modelo.excusas.ITipoExcusa;

public class Empleado {
    private final String nombre;
    private final String email;
    private final int nroLegajo;

    public Empleado(String nombre, String email, int nroLegajo) {
        this.nombre = nombre;
        this.email = email;
        this.nroLegajo = nroLegajo;
    }

    public void excusarse(String motivo, ITipoExcusa tipo, ManejadorDeExcusa manejador) {
        manejador.recibirExcusa(motivo, this, tipo);
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public int getNroLegajo() {
        return nroLegajo;
    }
}

