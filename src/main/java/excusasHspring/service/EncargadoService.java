package excusasHspring.service;

import excusasHspring.dto.CambioModoRequest;
import excusasHspring.dto.EncargadoRequest;
import excusasHspring.dto.EncargadoResponse;
import excusasHspring.modelo.empleados.encargados.*;
import excusasHspring.modelo.servicios.IAdministradorProntuario;
import excusasHspring.modelo.servicios.IEmailSender;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EncargadoService {

    private final Map<Integer, Encargado> encargados = new HashMap<>();
    private final IEmailSender emailSender;
    private final IAdministradorProntuario admin;

    public EncargadoService(IEmailSender emailSender, IAdministradorProntuario admin) {
        this.emailSender = emailSender;
        this.admin = admin;
    }

    public void registrarEncargado(EncargadoRequest req) {
        if (encargados.containsKey(req.getLegajo())) {
            throw new IllegalArgumentException("Ya existe un encargado con ese legajo.");
        }

        Encargado encargado = crearEncargado(req.getTipoEncargado(), req.getNombre(), req.getEmail(), req.getLegajo());
        encargados.put(req.getLegajo(), encargado);
    }

    public void cambiarModo(CambioModoRequest req) {
        Encargado encargado = encargados.get(req.getLegajo());
        if (encargado == null) {
            throw new IllegalArgumentException("Encargado no encontrado con legajo: " + req.getLegajo());
        }

        System.out.println("Cambio de modo solicitado para el encargado con legajo " + req.getLegajo() +
                " al modo: " + req.getNuevoModo() + " (sin efecto actual)");
    }

    public List<Encargado> listarEncargados() {
        return new ArrayList<>(encargados.values());
    }
    public List<EncargadoResponse> listarEncargadosDTO() {
        return encargados.values().stream()
                .map(e -> new EncargadoResponse(
                        e.getNombre(),
                        e.getClass().getSimpleName(),
                        e.getEstrategia().getNombre()
                ))
                .collect(Collectors.toList());
    }




    private Encargado crearEncargado(String tipo, String nombre, String email, int legajo) {
        return switch (tipo.toLowerCase()) {
            case "recepcionista" -> new Recepcionista(nombre, email, legajo, emailSender);
            case "supervisor"    -> new Supervisor(nombre, email, legajo, emailSender);
            case "gerenterrhh"   -> new GerenteRRHH(nombre, email, legajo, emailSender);
            case "ceo"           -> new CEO(nombre, email, legajo, emailSender, admin);
            default -> throw new IllegalArgumentException("Tipo de encargado no válido: " + tipo);
        };
    }
}
