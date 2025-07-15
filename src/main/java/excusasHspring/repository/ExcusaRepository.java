package excusasHspring.repository;

import excusasHspring.modelo.excusas.Excusa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExcusaRepository extends JpaRepository<Excusa, Long> {
    Optional<Excusa> findById(Long id);
    List<Excusa> findByEmpleadoNroLegajo(int nroLegajo);
    List<Excusa> findByEncargadoAceptoLegajo(int legajo);
}
