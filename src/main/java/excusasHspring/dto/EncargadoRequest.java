package excusasHspring.dto;

public class EncargadoRequest {
    private String nombre;
    private String email;
    private int legajo;
    private String tipoEncargado;
    private String modoEvaluacion;


    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getLegajo() { return legajo; }
    public void setLegajo(int legajo) { this.legajo = legajo; }

    public String getTipoEncargado() { return tipoEncargado; }
    public void setTipoEncargado(String tipoEncargado) { this.tipoEncargado = tipoEncargado; }

    public String getModoEvaluacion() { return modoEvaluacion; }
    public void setModoEvaluacion(String modoEvaluacion) { this.modoEvaluacion = modoEvaluacion; }
}
