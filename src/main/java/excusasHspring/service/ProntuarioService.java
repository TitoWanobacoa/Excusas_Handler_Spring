package excusasHspring.service;

import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.modelo.servicios.IAdministradorProntuario;
import excusasHspring.modelo.servicios.Prontuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProntuarioService {

    private final IAdministradorProntuario prontuario;

    public ProntuarioService(IAdministradorProntuario prontuario) {
        this.prontuario = prontuario;
    }

    public List<String> obtenerProntuarios() {
        return prontuario.getProntuarios().stream()
                .map(Prontuario::toString)
                .toList();
    }

    public List<Excusa> obtenerExcusasGuardadas() {
        return prontuario.getExcusasGuardadas();
    }

    public List<Excusa> filtrarExcusas(Integer legajo, String tipo, String encargado) {
        return prontuario.getExcusasGuardadas().stream()
                .filter(e -> legajo == null || e.getEmpleado().getNroLegajo() == legajo)
                .filter(e -> tipo == null || e.getTipo().getClass().getSimpleName().equalsIgnoreCase(tipo))
                .filter(e -> {
                    if (encargado == null) return true;
                    if (e.getEncargadoAcepto() == null) return false;
                    return encargado.equalsIgnoreCase(e.getEncargadoAcepto().getNombre())
                            || encargado.equalsIgnoreCase(e.getEncargadoAcepto().getRol())
                            || encargado.equals(String.valueOf(e.getEncargadoAcepto().getLegajo()));
                })

                .toList();
    }

    public List<String> filtrarProntuarios(String contiene) {
        return prontuario.getProntuarios().stream()
                .map(Prontuario::toString)
                .filter(p -> contiene == null || p.toLowerCase().contains(contiene.toLowerCase()))
                .toList();
    }
}
