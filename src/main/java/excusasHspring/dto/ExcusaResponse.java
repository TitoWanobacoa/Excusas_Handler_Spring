package excusasHspring.dto;

public class ExcusaResponse {
    private String nombreEmpleado;
    private String emailEmpleado;
    private int legajoEmpleado;
    private String tipo;
    private String descripcion;

    public ExcusaResponse(String nombreEmpleado, String emailEmpleado, int legajoEmpleado,
                          String tipo, String descripcion) {
        this.nombreEmpleado = nombreEmpleado;
        this.emailEmpleado = emailEmpleado;
        this.legajoEmpleado = legajoEmpleado;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }


    public String getNombreEmpleado() { return nombreEmpleado; }
    public String getEmailEmpleado() { return emailEmpleado; }
    public int getLegajoEmpleado() { return legajoEmpleado; }
    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
}
