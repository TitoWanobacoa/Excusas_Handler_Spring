package excusasHspring.modelo.excusas;

import excusasHspring.modelo.empleados.Empleado;

public class Excusa {
    private static long contadorId = 1;

    private final long id;
    private final Empleado empleado;
    private final ITipoExcusa tipo;
    private final String descripcion;
    private DatosEncargado encargadoAcepto;

    public Excusa(Empleado empleado, ITipoExcusa tipo, String descripcion) {
        this.id = contadorId++;
        this.empleado = empleado;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public long getId() {
        return id;
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

    public void setEncargadoAcepto(String nombre, String rol, int legajo) {
        this.encargadoAcepto = new DatosEncargado(nombre, rol, legajo);
    }

    public DatosEncargado getEncargadoAcepto() {
        return encargadoAcepto;
    }
}
