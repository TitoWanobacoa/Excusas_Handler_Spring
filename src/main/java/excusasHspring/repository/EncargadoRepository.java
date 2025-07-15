package excusasHspring.repository;

import excusasHspring.modelo.empleados.encargados.Encargado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncargadoRepository extends JpaRepository<Encargado, Integer> {
    List<Encargado> findByNroLegajo(int nroLegajo);
    boolean existsByNroLegajo(int nroLegajo);
    List<Encargado> findByNombreContainingIgnoreCase(String nombre);
}
