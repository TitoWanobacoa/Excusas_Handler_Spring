package excusasHspring.servicios;

import excusasHspring.modelo.excusas.Excusa;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdministradorProntuario extends Observable implements IAdministradorProntuario {

    private final List<String> prontuarios = new ArrayList<>();
    private final List<Excusa> excusasGuardadas = new ArrayList<>();

    @Override
    public void guardarProntuario(Excusa excusa) {
        String entrada = "Prontuario guardado para " + excusa.getEmpleado().getNombre();
        prontuarios.add(entrada);
        excusasGuardadas.add(excusa);

        System.out.println(entrada);
        this.notificar(new NotificacionExcusa(null, excusa));
    }

    @Override
    public List<String> getProntuarios() {
        return new ArrayList<>(prontuarios);
    }

    @Override
    public List<Excusa> getExcusasGuardadas() {
        return new ArrayList<>(excusasGuardadas);
    }

    @Override
    public void agregarObservador(IObservador o) {
        super.agregarObservador(o);
    }
}
