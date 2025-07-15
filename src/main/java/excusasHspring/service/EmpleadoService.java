package excusasHspring.service;

import excusasHspring.modelo.empleados.Empleado;
import excusasHspring.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    @Autowired
    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public Empleado registrarEmpleado(Empleado empleado) {
        if (empleadoRepository.existsById(empleado.getNroLegajo())) {
            throw new IllegalArgumentException("Ya existe un empleado con legajo " + empleado.getNroLegajo());
        }
        return empleadoRepository.save(empleado);
    }

    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    public Empleado buscarPorLegajo(int legajo) {
        return empleadoRepository.findById(legajo)
                .orElseThrow(() -> new NoSuchElementException("Empleado no encontrado con legajo: " + legajo));
    }

    public boolean existeLegajo(int legajo) {
        return empleadoRepository.existsById(legajo);
    }
}
