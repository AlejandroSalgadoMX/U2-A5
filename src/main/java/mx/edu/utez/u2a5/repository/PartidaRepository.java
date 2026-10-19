package mx.edu.utez.u2a5.repository;

import mx.edu.utez.u2a5.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {

    List<Partida> findByJugadorIdOrderByFechaDesc(Long jugadorId);
}
