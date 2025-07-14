package excusasHspring.dto;

import jakarta.validation.constraints.*;

public class EmpleadoRequest {

    @NotBlank
    private String nombre;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private Integer legajo;


    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getLegajo() { return legajo; }
    public void setLegajo(Integer legajo) { this.legajo = legajo; }
}
