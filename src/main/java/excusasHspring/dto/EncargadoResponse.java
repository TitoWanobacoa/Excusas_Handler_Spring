package excusasHspring.dto;

public class EncargadoResponse {
    private String nombre;
    private String tipo;
    private String estrategia;

    public EncargadoResponse(String nombre, String tipo, String estrategia) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.estrategia = estrategia;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEstrategia() {
        return estrategia;
    }
}
