package excusasHspring.controller;

import excusasHspring.dto.EmpleadoRequest;
import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping
    public ResponseEntity<?> registrarEmpleado(@Valid @RequestBody EmpleadoRequest request) {
        Empleado nuevo = new Empleado(request.getNombre(), request.getEmail(), request.getLegajo());
        try {
            Empleado registrado = empleadoService.registrarEmpleado(nuevo);
            return ResponseEntity.status(HttpStatus.CREATED).body(registrado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Empleado>> listarEmpleados() {
        return ResponseEntity.ok(empleadoService.listarEmpleados());
    }

    @GetMapping("/{legajo}")
    public ResponseEntity<?> obtenerPorLegajo(@PathVariable int legajo) {
        Empleado emp = empleadoService.buscarPorLegajo(legajo);
        if (emp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado");
        }
        return ResponseEntity.ok(emp);
    }
}
