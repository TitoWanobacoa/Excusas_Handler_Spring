package excusasHspring.modelo.servicios;

import excusasHspring.modelo.excusas.Excusa;
import java.util.List;

public interface IAdministradorProntuario {
    void guardarProntuario(Excusa excusa);
    void agregarObservador(IObservador o);
    List<Prontuario> getProntuarios();
    List<Excusa> getExcusasGuardadas();
}
