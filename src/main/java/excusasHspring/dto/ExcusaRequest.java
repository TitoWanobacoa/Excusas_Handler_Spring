package excusasHspring.dto;

import excusasHspring.modelo.excusas.TipoExcusa;
import jakarta.validation.constraints.*;

public class ExcusaRequest {

    @NotBlank
    private String nombreEmpleado;

    @Email
    @NotBlank
    private String emailEmpleado;

    @Min(1)
    private int legajoEmpleado;

    @NotNull
    private TipoExcusa tipo;

    @NotBlank
    private String descripcion;

    // Getters y setters
    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getEmailEmpleado() {
        return emailEmpleado;
    }

    public void setEmailEmpleado(String emailEmpleado) {
        this.emailEmpleado = emailEmpleado;
    }

    public int getLegajoEmpleado() {
        return legajoEmpleado;
    }

    public void setLegajoEmpleado(int legajoEmpleado) {
        this.legajoEmpleado = legajoEmpleado;
    }

    public TipoExcusa getTipo() {
        return tipo;
    }

    public void setTipo(TipoExcusa tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
