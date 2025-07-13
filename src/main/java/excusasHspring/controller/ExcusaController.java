package excusasHspring.controller;

import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.modelo.excusas.ITipoExcusa;
import excusasHspring.modelo.excusas.Compleja;
import excusasHspring.modelo.excusas.Moderada;
import excusasHspring.modelo.excusas.Trivial;
import excusasHspring.modelo.excusas.Inverosimil;
import excusasHspring.modelo.empleados.encargados.evaluacion.EvaluacionNormal;
import excusasHspring.modelo.empleados.encargados.evaluacion.EvaluacionProductiva;
import excusasHspring.modelo.empleados.encargados.evaluacion.EvaluacionVaga;
import excusasHspring.modelo.empleados.encargados.ManejadorDeExcusa;
import excusasHspring.servicios.AdministradorProntuario;

import excusasHspring.modelo.empleados.encargados.Recepcionista;
import excusasHspring.modelo.empleados.encargados.Supervisor;
import excusasHspring.modelo.empleados.encargados.GerenteRRHH;
import excusasHspring.modelo.empleados.encargados.CEO;

import excusasHspring.servicios.EmailSenderFake;
import excusasHspring.servicios.IEmailSender;
import org.springframework.web.bind.annotation.*;

import excusasHspring.dto.ExcusaResponse;

import java.util.*;

@RestController
@RequestMapping("/api/excusas")
public class ExcusaController {

    private final List<Excusa> excusasProcesadas = new ArrayList<>();
    private final List<String> notificaciones = new ArrayList<>();
    private final AdministradorProntuario prontuario;
    private final ManejadorDeExcusa manejador;

    public ExcusaController() {
        AdministradorProntuario prontuario = AdministradorProntuario.getInstancia();
        IEmailSender emailSender = new EmailSenderFake();

        Recepcionista recepcionista = new Recepcionista("Ana", "ana@empresa.com", 1001, emailSender);
        Supervisor supervisor = new Supervisor("Luis", "luis@empresa.com", 1002, emailSender);
        GerenteRRHH gerente = new GerenteRRHH("Marta", "marta@empresa.com", 1003, emailSender);
        CEO ceo = new CEO("Carlos", "carlos@empresa.com", 1004, emailSender, prontuario);

        recepcionista.setEstrategia(new EvaluacionNormal());
        supervisor.setEstrategia(new EvaluacionVaga());
        gerente.setEstrategia(new EvaluacionProductiva(emailSender));
        ceo.setEstrategia(new EvaluacionNormal());

        prontuario.agregarObservador(ceo);

        this.prontuario = AdministradorProntuario.getInstancia();
        this.manejador = new ManejadorDeExcusa(recepcionista, supervisor, gerente, ceo);
    }


    @PostMapping("/enviar")
    public Map<String, Object> enviarExcusa(@RequestBody ExcusaRequest request) {
        Empleado empleado = new Empleado(
                request.getNombreEmpleado(),
                request.getEmailEmpleado(),
                request.getLegajoEmpleado()
        );
        ITipoExcusa tipo = switch (request.getTipo().toLowerCase()) {
            case "compleja" -> new Compleja();
            case "moderada" -> new Moderada();
            case "trivial" -> new Trivial();
            case "inverosimil" -> new Inverosimil();
            default -> throw new IllegalArgumentException("Tipo de excusa desconocido: " + request.getTipo());
        };

        Excusa excusa = new Excusa(empleado, tipo, request.getDescripcion());
        manejador.recibirExcusa(request.getDescripcion(), empleado, tipo);
        excusasProcesadas.add(excusa);

        ExcusaResponse respuestaDto = new ExcusaResponse(
                empleado.getNombre(),
                empleado.getEmail(),
                empleado.getNroLegajo(),
                tipo.getClass().getSimpleName(),
                excusa.getDescripcion()
        );

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("excusa", respuestaDto);
        respuesta.put("notificaciones", new ArrayList<>(notificaciones));
        respuesta.put("prontuarios", prontuario.getProntuarios());
        return respuesta;
    }

    @GetMapping("/procesadas")
    public List<ExcusaResponse> listarExcusas() {
        return excusasProcesadas.stream()
                .map(excusa -> new ExcusaResponse(
                        excusa.getEmpleado().getNombre(),
                        excusa.getEmpleado().getEmail(),
                        excusa.getEmpleado().getNroLegajo(),
                        excusa.getTipo().getClass().getSimpleName(),
                        excusa.getDescripcion()
                ))
                .toList();
    }


    @GetMapping("/notificaciones")
    public List<String> getNotificaciones() {
        return notificaciones;
    }

    @GetMapping("/prontuarios")
    public List<String> getProntuarios() {
        return prontuario.getProntuarios();
    }

    public static class ExcusaRequest {
        private String nombreEmpleado;
        private String emailEmpleado;
        private int legajoEmpleado;
        private String tipo;
        private String descripcion;

        public String getNombreEmpleado() { return nombreEmpleado; }
        public void setNombreEmpleado(String nombreEmpleado) { this.nombreEmpleado = nombreEmpleado; }

        public String getEmailEmpleado() { return emailEmpleado; }
        public void setEmailEmpleado(String emailEmpleado) { this.emailEmpleado = emailEmpleado; }

        public int getLegajoEmpleado() { return legajoEmpleado; }
        public void setLegajoEmpleado(int legajoEmpleado) { this.legajoEmpleado = legajoEmpleado; }

        public String getTipo() { return tipo; }
        public void setTipo(String tipo) { this.tipo = tipo; }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    }

}
