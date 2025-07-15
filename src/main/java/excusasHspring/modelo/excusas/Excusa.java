package excusasHspring.modelo.excusas;

import excusasHspring.modelo.empleados.Empleado;
import jakarta.persistence.*;

@Entity
@Table(name = "excusas")
public class Excusa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Empleado empleado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoExcusa tipo;

    @Column(nullable = false)
    private String descripcion;

    @Embedded
    private DatosEncargado encargadoAcepto;

    protected Excusa() {

    }

    public Excusa(Empleado empleado, TipoExcusa tipo, String descripcion) {
        this.empleado = empleado;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public TipoExcusa getTipo() {
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
