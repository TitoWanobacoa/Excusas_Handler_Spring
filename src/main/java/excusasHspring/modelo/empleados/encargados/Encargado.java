package excusasHspring.modelo.empleados.encargados;

import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.modelo.empleados.encargados.evaluacion.IEvaluacionExcusa;
import excusasHspring.modelo.empleados.encargados.evaluacion.ModoEvaluacion;
import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.modelo.excusas.TipoExcusa;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "encargados")
public abstract class Encargado extends Empleado {

    @Transient
    private Encargado siguiente;

    @Transient
    protected IEvaluacionExcusa estrategia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModoEvaluacion modo;

    protected Encargado() {}

    public Encargado(String nombre, String email, int legajo) {
        super(nombre, email, legajo);
    }

    public void setSiguiente(Encargado siguiente) {
        this.siguiente = siguiente;
    }

    public Encargado getSiguiente() {
        return siguiente;
    }

    public final void manejarExcusa(Excusa excusa) {
        estrategia.evaluar(this, excusa);
    }

    public void aceptarExcusa(Excusa excusa) {
        excusa.setEncargadoAcepto(this.getNombre(), this.getClass().getSimpleName(), this.getNroLegajo());
        procesar(excusa);
    }

    public void pasarAlSiguiente(Excusa excusa) {
        if (siguiente != null) {
            siguiente.manejarExcusa(excusa);
        } else {
            System.out.println("Excusa rechazada: necesitamos pruebas contundentes.");
        }
    }

    public boolean aceptaTrivial()     { return false; }
    public boolean aceptaModerada()    { return false; }
    public boolean aceptaCompleja()    { return false; }
    public boolean aceptaInverosimil() { return false; }

    public boolean aceptaExcusa(TipoExcusa tipo) {
        return switch (tipo) {
            case TRIVIAL     -> aceptaTrivial();
            case MODERADA    -> aceptaModerada();
            case COMPLEJA    -> aceptaCompleja();
            case INVEROSIMIL -> aceptaInverosimil();
        };
    }

    public void setEstrategia(IEvaluacionExcusa estrategia) {
        this.estrategia = estrategia;
    }

    public IEvaluacionExcusa getEstrategia() {
        return this.estrategia;
    }

    public ModoEvaluacion getModo() {
        return modo;
    }

    public void setModo(ModoEvaluacion modo) {
        this.modo = modo;
    }

    public abstract void procesar(Excusa excusa);
}
