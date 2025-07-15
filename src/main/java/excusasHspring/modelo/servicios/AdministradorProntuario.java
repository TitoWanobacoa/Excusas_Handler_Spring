package excusasHspring.modelo.servicios;

import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.repository.ProntuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdministradorProntuario extends Observable implements IAdministradorProntuario {

    private final ProntuarioRepository prontuarioRepository;

    @Autowired
    public AdministradorProntuario(ProntuarioRepository prontuarioRepository) {
        this.prontuarioRepository = prontuarioRepository;
    }

    @Override
    public void guardarProntuario(Excusa excusa) {
        String mensaje = "Prontuario guardado para " + excusa.getEmpleado().getNombre();
        Prontuario prontuario = new Prontuario(excusa, mensaje);
        prontuarioRepository.save(prontuario);

        System.out.println(mensaje);
        this.notificar(new NotificacionExcusa(null, excusa));
    }

    @Override
    public List<Excusa> getExcusasGuardadas() {
        return prontuarioRepository.findAll().stream()
                .map(Prontuario::getExcusa)
                .toList();
    }


    @Override
    public List<Prontuario> getProntuarios() {
        return prontuarioRepository.findAll();
    }


    @Override
    public void agregarObservador(IObservador o) {
        super.agregarObservador(o);
    }
}
