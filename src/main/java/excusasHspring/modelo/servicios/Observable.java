package excusasHspring.modelo.servicios;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private final List<IObservador> observadores = new ArrayList<>();

    public void agregarObservador(IObservador o) {
        observadores.add(o);
    }

    public void quitarObservador(IObservador o) {
        observadores.remove(o);
    }

    protected void notificar(NotificacionExcusa notificacion) {
        for (IObservador o : observadores) {
            o.actualizar(notificacion);
        }
    }

}
