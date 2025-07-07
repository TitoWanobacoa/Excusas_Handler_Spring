package excusasHspring.servicios;

import modelo.excusas.Excusa;

public interface IAdministradorProntuario {
    void guardarProntuario(Excusa excusa);
    void agregarObservador(IObservador o);
}
