package excusasHspring.service;

import excusasHspring.modelo.empleados.Empleado;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmpleadoService {

    private final Map<Integer, Empleado> empleados = new HashMap<>();

    public Empleado registrarEmpleado(Empleado empleado) {
        if (empleados.containsKey(empleado.getNroLegajo())) {
            throw new IllegalArgumentException("Ya existe un empleado con legajo " + empleado.getNroLegajo());
        }
        empleados.put(empleado.getNroLegajo(), empleado);
        return empleado;
    }

    public List<Empleado> listarEmpleados() {
        return new ArrayList<>(empleados.values());
    }

    public Empleado buscarPorLegajo(int legajo) {
        return empleados.get(legajo);
    }

    public boolean existeLegajo(int legajo) {
        return empleados.containsKey(legajo);
    }
}

