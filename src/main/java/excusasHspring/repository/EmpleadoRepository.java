package excusasHspring.repository;

import excusasHspring.modelo.empleados.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findByNroLegajo(int nroLegajo);
    boolean existsByNroLegajo(int nroLegajo);
    List<Empleado> findByNombreContainingIgnoreCase(String nombre);
}
