package excusasHspring.servicios;

import excusasHspring.modelo.empleados.encargados.Encargado;
import excusasHspring.modelo.excusas.Excusa;

public class NotificacionExcusa {
    private final Encargado encargado;
    private final Excusa excusa;

    public NotificacionExcusa(Encargado encargado, Excusa excusa) {
        this.encargado = encargado;
        this.excusa = excusa;
    }

    public Encargado getEncargado() {
        return encargado;
    }

    public Excusa getExcusa() {
        return excusa;
    }
}
