package excusasHspring.service;

import excusasHspring.dto.ExcusaRequest;
import excusasHspring.dto.ExcusaResponse;
import excusasHspring.exception.TipoExcusaInvalidoException;
import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.modelo.empleados.encargados.*;
import excusasHspring.modelo.empleados.encargados.evaluacion.*;
import excusasHspring.modelo.excusas.*;
import excusasHspring.servicios.IAdministradorProntuario;
import excusasHspring.servicios.IEmailSender;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExcusaService {

    private final List<Excusa> excusasProcesadas = new ArrayList<>();
    private final List<Excusa> rechazadas = new ArrayList<>();
    private final List<String> notificaciones = new ArrayList<>();

    private final IAdministradorProntuario prontuario;
    private final ManejadorDeExcusa manejador;

    public ExcusaService(IEmailSender emailSender, IAdministradorProntuario prontuario) {
        this.prontuario = prontuario;

        Recepcionista recepcionista = new Recepcionista("Ana", "ana@empresa.com", 1001, emailSender);
        Supervisor supervisor = new Supervisor("Luis", "luis@empresa.com", 1002, emailSender);
        GerenteRRHH gerente = new GerenteRRHH("Marta", "marta@empresa.com", 1003, emailSender);
        CEO ceo = new CEO("Carlos", "carlos@empresa.com", 1004, emailSender, prontuario);
        RechazadorExcusas rechazador = new RechazadorExcusas("Sistema", "rechazo@excusas.sa", 9999, emailSender);

        recepcionista.setEstrategia(new EvaluacionNormal());
        supervisor.setEstrategia(new EvaluacionVaga());
        gerente.setEstrategia(new EvaluacionProductiva(emailSender, prontuario));
        ceo.setEstrategia(new EvaluacionNormal());

        prontuario.agregarObservador(ceo);

        this.manejador = new ManejadorDeExcusa(recepcionista, supervisor, gerente, ceo, rechazador);
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

        ITipoExcusa tipo = construirTipoExcusa(request.getTipo());

        Excusa excusa = new Excusa(empleado, tipo, request.getDescripcion());
        excusasProcesadas.add(excusa);
        manejador.recibirExcusa(request.getDescripcion(), empleado, tipo);

        Excusa rechazada = manejador.getRechazador().getUltimaExcusaRechazada();
        if (rechazada != null &&
                rechazada.getEmpleado().getNroLegajo() == empleado.getNroLegajo() &&
                rechazada.getDescripcion().equals(request.getDescripcion())) {
            rechazadas.add(rechazada);
        }

        ExcusaResponse respuestaDto = toResponse(excusa);

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("excusa", respuestaDto);
        respuesta.put("notificaciones", new ArrayList<>(notificaciones));
        return respuesta;
    }

    private ITipoExcusa construirTipoExcusa(TipoExcusa tipo) {
        return switch (tipo) {
            case COMPLEJA -> new Compleja();
            case MODERADA -> new Moderada();
            case TRIVIAL -> new Trivial();
            case INVEROSIMIL -> new Inverosimil();
        };
    }

    public List<ExcusaResponse> listarExcusas() {
        return excusasProcesadas.stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ExcusaResponse> buscarPorLegajo(int legajo) {
        return excusasProcesadas.stream()
                .filter(e -> e.getEmpleado().getNroLegajo() == legajo)
                .map(this::toResponse)
                .toList();
    }

    public List<ExcusaResponse> buscarConFiltros(String motivo, String encargado) {
        return excusasProcesadas.stream()
                .filter(e -> {
                    boolean coincideMotivo = motivo == null
                            || e.getTipo().getClass().getSimpleName().equalsIgnoreCase(motivo);

                    boolean coincideEncargado = encargado == null;
                    if (e.getEncargadoAcepto() != null) {
                        coincideEncargado = coincideEncargado ||
                                encargado.equalsIgnoreCase(e.getEncargadoAcepto().nombre()) ||
                                encargado.equalsIgnoreCase(e.getEncargadoAcepto().rol()) ||
                                encargado.equals(String.valueOf(e.getEncargadoAcepto().legajo()));
                    }

                    return coincideMotivo && coincideEncargado;
                })
                .map(this::toResponse)
                .toList();
    }

    public List<ExcusaResponse> obtenerRechazadas() {
        return rechazadas.stream()
                .map(this::toResponse)
                .toList();
    }

    public List<String> getNotificaciones() {
        return notificaciones;
    }

    public ExcusaResponse buscarPorId(long id) {
        return excusasProcesadas.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .map(this::toResponse)
                .orElseThrow(() -> new NoSuchElementException("Excusa no encontrada con id: " + id));
    }

    public Excusa getExcusaById(long id) {
        return excusasProcesadas.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Excusa no encontrada con id: " + id));
    }

    public boolean eliminarPorId(long id) {
        boolean eliminada = excusasProcesadas.removeIf(e -> e.getId() == id);
        rechazadas.removeIf(e -> e.getId() == id);
        return eliminada;
    }

    public boolean fueRechazada(long id) {
        return rechazadas.stream().anyMatch(e -> e.getId() == id);
    }

    private ExcusaResponse toResponse(Excusa excusa) {
        String encargadoNombre = "";
        String encargadoRol = "";
        int encargadoLegajo = -1;

        if (excusa.getEncargadoAcepto() != null) {
            encargadoNombre = excusa.getEncargadoAcepto().nombre();
            encargadoRol = excusa.getEncargadoAcepto().rol();
            encargadoLegajo = excusa.getEncargadoAcepto().legajo();
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
}
