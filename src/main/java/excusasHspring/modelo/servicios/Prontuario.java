package excusasHspring.modelo.servicios;

import excusasHspring.modelo.excusas.Excusa;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "prontuarios")
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Excusa excusa;

    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false)
    private LocalDateTime fecha;

    protected Prontuario() {}

    public Prontuario(Excusa excusa, String mensaje) {
        this.excusa = excusa;
        this.mensaje = mensaje;
        this.fecha = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Excusa getExcusa() {
        return excusa;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Prontuario{" +
                "descripcion='" + excusa.getDescripcion() + '\'' +
                ", tipo=" + excusa.getTipo().getClass().getSimpleName() +
                ", empleado=" + excusa.getEmpleado().getNombre() +
                ", encargado=" + (excusa.getEncargadoAcepto() != null ? excusa.getEncargadoAcepto().getNombre() : "Ninguno") +
                ", mensaje='" + mensaje + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
