package excusasHspring.modelo.empleados.encargados;

import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.modelo.excusas.ITipoExcusa;
import excusasHspring.modelo.excusas.TipoExcusa;
import excusasHspring.modelo.servicios.EmailSenderFake;

public class ManejadorDeExcusa {
    private final Encargado encargadoInicial;
    private final RechazadorExcusas rechazador;

    public ManejadorDeExcusa(Encargado... encargados) {
        for (int i = 0; i < encargados.length - 1; i++) {
            encargados[i].setSiguiente(encargados[i + 1]);
        }

        this.rechazador = new RechazadorExcusas("Rechazador", "rechazo@excusas.sa", 9999, new EmailSenderFake());
        encargados[encargados.length - 1].setSiguiente(rechazador);
        this.encargadoInicial = encargados[0];
    }

    public void recibirExcusa(String descripcion, Empleado empleado, TipoExcusa tipo) {
        Excusa excusa = new Excusa(empleado, tipo, descripcion);
        encargadoInicial.manejarExcusa(excusa);
    }

    public RechazadorExcusas getRechazador() {
        return rechazador;
    }
}
