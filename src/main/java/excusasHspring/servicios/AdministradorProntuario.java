package excusasHspring.servicios;

import excusasHspring.modelo.excusas.Excusa;
import java.util.ArrayList;
import java.util.List;

public class AdministradorProntuario extends Observable implements IAdministradorProntuario {
    private static final AdministradorProntuario instancia = new AdministradorProntuario();
    private final List<String> prontuarios = new ArrayList<>();

    private AdministradorProntuario() {}

    public static AdministradorProntuario getInstancia() {
        return instancia;
    }

    @Override
    public void guardarProntuario(Excusa excusa) {
        String entrada = "Prontuario guardado para " + excusa.getEmpleado().getNombre();
        prontuarios.add(entrada);

        System.out.println(entrada);
        this.notificar(new NotificacionExcusa(null, excusa));
    }

    public List<String> getProntuarios() {
        return new ArrayList<>(prontuarios); // copia defensiva
    }

    @Override
    public void agregarObservador(IObservador o) {
        super.agregarObservador(o);
    }
}
