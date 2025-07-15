package excusasHspring.modelo.excusas;

import jakarta.persistence.Embeddable;

@Embeddable
public class DatosEncargado {

    private String nombre;
    private String rol;
    private int legajo;

    protected DatosEncargado() {}

    public DatosEncargado(String nombre, String rol, int legajo) {
        this.nombre = nombre;
        this.rol = rol;
        this.legajo = legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }

    public int getLegajo() {
        return legajo;
    }

    @Override
    public String toString() {
        return nombre + " (" + rol + " - " + legajo + ")";
    }
}
