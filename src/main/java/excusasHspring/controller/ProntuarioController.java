package excusasHspring.controller;

import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.service.ProntuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prontuarios")
public class ProntuarioController {

    private final ProntuarioService prontuarioService;

    public ProntuarioController(ProntuarioService prontuarioService) {
        this.prontuarioService = prontuarioService;
    }

    @GetMapping
    public List<Excusa> obtenerExcusasGuardadas() {
        return prontuarioService.obtenerExcusasGuardadas();
    }

    @GetMapping("/logs")
    public List<String> obtenerProntuarios() {
        return prontuarioService.obtenerProntuarios();
    }

    @GetMapping("/filtrar")
    public List<Excusa> filtrarExcusas(
            @RequestParam(required = false) Integer legajo,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String encargado
    ) {
        return prontuarioService.filtrarExcusas(legajo, tipo, encargado);
    }

    @GetMapping("/logs/filtrar")
    public List<String> filtrarProntuarios(
            @RequestParam(required = false) String contiene
    ) {
        return prontuarioService.filtrarProntuarios(contiene);
    }
}
