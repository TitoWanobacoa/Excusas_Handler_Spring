package excusasHspring.modelo.empleados.encargados;

import excusasHspring.modelo.empleados.Empleado ;
import excusasHspring.modelo.empleados.encargados.evaluacion.IEvaluacionExcusa ;
import excusasHspring.modelo.excusas.Excusa;

public abstract class Encargado extends Empleado {
    private Encargado siguiente;
    protected IEvaluacionExcusa estrategia;

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

    public void setEstrategia(IEvaluacionExcusa estrategia) {
        this.estrategia = estrategia;
    }

    public abstract void procesar(Excusa excusa);
}
