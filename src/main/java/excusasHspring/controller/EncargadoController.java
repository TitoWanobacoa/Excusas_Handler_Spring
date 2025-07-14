package excusasHspring.controller;

import excusasHspring.dto.CambioModoRequest;
import excusasHspring.dto.EncargadoRequest;
import excusasHspring.modelo.empleados.encargados.Encargado;
import excusasHspring.service.EncargadoService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import excusasHspring.dto.EncargadoResponse;


import java.util.List;

@RestController
@RequestMapping("/api/encargados")
public class EncargadoController {

    private final EncargadoService encargadoService;

    public EncargadoController(EncargadoService encargadoService) {
        this.encargadoService = encargadoService;
    }

    @PostMapping
    public ResponseEntity<?> crearEncargado(@RequestBody EncargadoRequest request) {
        try {
            encargadoService.registrarEncargado(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Encargado registrado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/modo")
    public ResponseEntity<?> cambiarModo(@RequestBody CambioModoRequest request) {
        try {
            encargadoService.cambiarModo(request);
            return ResponseEntity.ok("Modo de evaluación actualizado.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<EncargadoResponse>> listarEncargados() {
        return ResponseEntity.ok(encargadoService.listarEncargadosDTO());
    }

}
