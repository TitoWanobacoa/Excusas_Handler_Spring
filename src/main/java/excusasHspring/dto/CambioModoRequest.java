package excusasHspring.dto;

public class CambioModoRequest {
    private int legajo;
    private String nuevoModo;

    public int getLegajo() { return legajo; }
    public void setLegajo(int legajo) { this.legajo = legajo; }

    public String getNuevoModo() { return nuevoModo; }
    public void setNuevoModo(String nuevoModo) { this.nuevoModo = nuevoModo; }
}
