package excusasHspring.modelo.empleados;

import excusasHspring.modelo.empleados.encargados.ManejadorDeExcusa;
import excusasHspring.modelo.excusas.ITipoExcusa;
import excusasHspring.modelo.excusas.TipoExcusa;
import jakarta.persistence.*;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    private int nroLegajo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String email;

    protected Empleado() {
    }

    public Empleado(String nombre, String email, int nroLegajo) {
        this.nombre = nombre;
        this.email = email;
        this.nroLegajo = nroLegajo;
    }

    public void excusarse(String motivo, TipoExcusa tipo, ManejadorDeExcusa manejador) {
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
