package excusasHspring.servicios;

import excusasHspring.modelo.excusas.Excusa;
import java.util.List;

public interface IAdministradorProntuario {
    void guardarProntuario(Excusa excusa);
    void agregarObservador(IObservador o);
    List<String> getProntuarios();
    List<Excusa> getExcusasGuardadas();
}
