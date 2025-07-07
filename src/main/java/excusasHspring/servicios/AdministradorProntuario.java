package excusasHspring.servicios;

import modelo.excusas.Excusa;

public class AdministradorProntuario extends Observable implements IAdministradorProntuario {
    private static AdministradorProntuario instancia;

    private AdministradorProntuario() {}

    public static AdministradorProntuario getInstancia() {
        if (instancia == null) {
            instancia = new AdministradorProntuario();
        }
        return instancia;
    }

    @Override
    public void guardarProntuario(Excusa excusa) {
        System.out.println("Prontuario guardado para " + excusa.getEmpleado().getNombre());

        this.notificar(new NotificacionExcusa(null, excusa));
    }

    @Override
    public void agregarObservador(IObservador o) {
        super.agregarObservador(o);
    }
}

