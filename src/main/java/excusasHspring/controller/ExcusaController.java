package excusasHspring.controller;

import excusasHspring.dto.ExcusaRequest;
import excusasHspring.dto.ExcusaResponse;
import excusasHspring.modelo.excusas.Excusa;
import excusasHspring.modelo.excusas.DatosEncargado;
import excusasHspring.service.ExcusaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/excusas")
public class ExcusaController {

    private final ExcusaService service;

    public ExcusaController(ExcusaService service) {
        this.service = service;
    }

    @PostMapping("/enviar")
    public ResponseEntity<Map<String, Object>> enviarExcusa(@Valid @RequestBody ExcusaRequest request) {
        Map<String, Object> respuesta = service.procesarExcusa(request);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/procesadas")
    public ResponseEntity<List<ExcusaResponse>> listarExcusas() {
        return ResponseEntity.ok(service.listarExcusas());
    }

    @GetMapping("/notificaciones")
    public ResponseEntity<List<String>> getNotificaciones() {
        return ResponseEntity.ok(service.getNotificaciones());
    }

    @GetMapping("/{legajo}")
    public ResponseEntity<?> buscarPorLegajo(@PathVariable int legajo) {
        List<ExcusaResponse> resultados = service.buscarPorLegajo(legajo);
        if (resultados.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body("No se encontraron excusas para el legajo: " + legajo);
        }
        return ResponseEntity.ok(resultados);
    }

    @GetMapping
    public ResponseEntity<List<ExcusaResponse>> buscarConFiltros(
            @RequestParam(required = false) String motivo,
            @RequestParam(required = false) String encargado
    ) {
        return ResponseEntity.ok(service.buscarConFiltros(motivo, encargado));
    }

    @GetMapping("/rechazadas")
    public ResponseEntity<List<ExcusaResponse>> obtenerRechazadas() {
        return ResponseEntity.ok(service.obtenerRechazadas());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable long id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarPorId(@RequestParam long id) {
        boolean eliminada = service.eliminarPorId(id);
        if (!eliminada) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una excusa con ese ID");
        }
        return ResponseEntity.ok("Excusa eliminada con éxito");
    }

    @GetMapping("/aceptada/{id}")
    public ResponseEntity<?> encargadoAcepto(@PathVariable long id) {
        try {
            Excusa excusa = service.getExcusaById(id);
            DatosEncargado encargado = excusa.getEncargadoAcepto();
            if (encargado == null) {
                return ResponseEntity.ok("La excusa no fue aceptada por ningún encargado");
            }
            return ResponseEntity.ok(encargado);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Excusa no encontrada");
        }
    }

    @GetMapping("/rechazada/{id}")
    public ResponseEntity<String> fueRechazada(@PathVariable long id) {
        boolean rechazada = service.fueRechazada(id);
        return ResponseEntity.ok(rechazada ? "La excusa fue rechazada" : "La excusa no fue rechazada");
    }
}
