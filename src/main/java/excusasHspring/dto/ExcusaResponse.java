package excusasHspring.dto;

public class ExcusaResponse {
    private long id;

    private String nombreEmpleado;
    private String emailEmpleado;
    private int legajoEmpleado;
    private String tipoExcusa;
    private String descripcion;

    private String encargadoAceptoNombre;
    private String encargadoAceptoRol;
    private int encargadoAceptoLegajo;

    public ExcusaResponse(
            long id,
            String nombreEmpleado,
            String emailEmpleado,
            int legajoEmpleado,
            String tipoExcusa,
            String descripcion,
            String encargadoAceptoNombre,
            String encargadoAceptoRol,
            int encargadoAceptoLegajo
    ) {
        this.id = id;
        this.nombreEmpleado = nombreEmpleado;
        this.emailEmpleado = emailEmpleado;
        this.legajoEmpleado = legajoEmpleado;
        this.tipoExcusa = tipoExcusa;
        this.descripcion = descripcion;
        this.encargadoAceptoNombre = encargadoAceptoNombre;
        this.encargadoAceptoRol = encargadoAceptoRol;
        this.encargadoAceptoLegajo = encargadoAceptoLegajo;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getTipoExcusa() {
        return tipoExcusa;
    }

    public void setTipoExcusa(String tipoExcusa) {
        this.tipoExcusa = tipoExcusa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEncargadoAceptoNombre() {
        return encargadoAceptoNombre;
    }

    public void setEncargadoAceptoNombre(String encargadoAceptoNombre) {
        this.encargadoAceptoNombre = encargadoAceptoNombre;
    }

    public String getEncargadoAceptoRol() {
        return encargadoAceptoRol;
    }

    public void setEncargadoAceptoRol(String encargadoAceptoRol) {
        this.encargadoAceptoRol = encargadoAceptoRol;
    }

    public int getEncargadoAceptoLegajo() {
        return encargadoAceptoLegajo;
    }

    public void setEncargadoAceptoLegajo(int encargadoAceptoLegajo) {
        this.encargadoAceptoLegajo = encargadoAceptoLegajo;
    }
}
