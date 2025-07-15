package excusasHspring.service;

import excusasHspring.dto.ExcusaRequest;
import excusasHspring.dto.ExcusaResponse;
import excusasHspring.exception.TipoExcusaInvalidoException;
import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.modelo.empleados.encargados.*;
import excusasHspring.modelo.empleados.encargados.evaluacion.EvaluacionFactory;
import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.modelo.excusas.TipoExcusa;
import excusasHspring.modelo.servicios.IAdministradorProntuario;
import excusasHspring.modelo.servicios.IEmailSender;
import excusasHspring.repository.EncargadoRepository;
import excusasHspring.repository.ExcusaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExcusaService {

    private final ExcusaRepository excusaRepository;
    private final ManejadorDeExcusa manejador;

    @Autowired
    public ExcusaService(
            ExcusaRepository excusaRepository,
            EncargadoRepository encargadoRepository,
            IEmailSender emailSender,
            IAdministradorProntuario prontuario
    ) {
        this.excusaRepository = excusaRepository;

        List<Encargado> encargados = encargadoRepository.findAll();

        for (Encargado encargado : encargados) {
            encargado.setEstrategia(
                    EvaluacionFactory.crearEstrategia(encargado.getModo(), emailSender, prontuario)
            );
        }

        this.manejador = new ManejadorDeExcusa(encargados.toArray(new Encargado[0]));

    }

    public Map<String, Object> procesarExcusa(ExcusaRequest request) {
        if (request.getTipo() == null) {
            throw new TipoExcusaInvalidoException("nulo o no reconocido");
        }

        Empleado empleado = new Empleado(
                request.getNombreEmpleado(),
                request.getEmailEmpleado(),
                request.getLegajoEmpleado()
        );

        Excusa excusa = new Excusa(empleado, request.getTipo(), request.getDescripcion());

        manejador.recibirExcusa(request.getDescripcion(), empleado, request.getTipo());

        excusaRepository.save(excusa);

        ExcusaResponse respuestaDto = toResponse(excusa);

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("excusa", respuestaDto);
        respuesta.put("notificaciones", List.of()); // si en el futuro se guardan
        return respuesta;
    }

    public List<ExcusaResponse> listarExcusas() {
        return excusaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ExcusaResponse> buscarPorLegajo(int legajo) {
        return excusaRepository.findByEmpleadoNroLegajo(legajo).stream()
                .map(this::toResponse)
                .toList();
    }

    public ExcusaResponse buscarPorId(long id) {
        return excusaRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NoSuchElementException("Excusa no encontrada con id: " + id));
    }

    public Excusa getExcusaById(long id) {
        return excusaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Excusa no encontrada con id: " + id));
    }

    public boolean eliminarPorId(long id) {
        if (excusaRepository.existsById(id)) {
            excusaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean fueRechazada(long id) {
        return false;
    }

    private ExcusaResponse toResponse(Excusa excusa) {
        String encargadoNombre = "";
        String encargadoRol = "";
        int encargadoLegajo = -1;

        if (excusa.getEncargadoAcepto() != null) {
            encargadoNombre = excusa.getEncargadoAcepto().getNombre();
            encargadoRol = excusa.getEncargadoAcepto().getRol();
            encargadoLegajo = excusa.getEncargadoAcepto().getLegajo();
        }

        return new ExcusaResponse(
                excusa.getId(),
                excusa.getEmpleado().getNombre(),
                excusa.getEmpleado().getEmail(),
                excusa.getEmpleado().getNroLegajo(),
                excusa.getTipo().getClass().getSimpleName(),
                excusa.getDescripcion(),
                encargadoNombre,
                encargadoRol,
                encargadoLegajo
        );
    }
    public List<String> getNotificaciones() {
        return List.of("Notificación 1", "Notificación 2");
    }

    public List<ExcusaResponse> buscarConFiltros(String motivo, String encargado) {
        return excusaRepository.findAll().stream()
                .filter(e -> {
                    boolean coincideMotivo = motivo == null
                            || e.getTipo().getClass().getSimpleName().equalsIgnoreCase(motivo);

                    boolean coincideEncargado = encargado == null;
                    if (e.getEncargadoAcepto() != null) {
                        coincideEncargado = coincideEncargado ||
                                encargado.equalsIgnoreCase(e.getEncargadoAcepto().getNombre()) ||
                                encargado.equalsIgnoreCase(e.getEncargadoAcepto().getRol()) ||
                                encargado.equals(String.valueOf(e.getEncargadoAcepto().getLegajo()));
                    }

                    return coincideMotivo && coincideEncargado;
                })
                .map(this::toResponse)
                .toList();
    }

    public List<ExcusaResponse> obtenerRechazadas() {
        return excusaRepository.findAll().stream()
                .filter(e -> e.getEncargadoAcepto() == null)
                .map(this::toResponse)
                .toList();
    }

}
