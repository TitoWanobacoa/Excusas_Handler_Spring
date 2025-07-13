package excusasHspring.modelo.excusas;

import excusasHspring.modelo.empleados.Empleado;

public class Excusa {
    private final Empleado empleado;
    private final ITipoExcusa tipo;
    private final String descripcion;

    public Excusa(Empleado empleado, ITipoExcusa tipo, String descripcion) {
        this.empleado = empleado;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public ITipoExcusa getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

