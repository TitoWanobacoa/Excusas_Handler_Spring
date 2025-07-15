package excusasHspring.repository;

import excusasHspring.modelo.servicios.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
    List<Prontuario> findByExcusaId(Long id);
    List<Prontuario> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);
}
