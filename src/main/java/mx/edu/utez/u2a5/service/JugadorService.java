package mx.edu.utez.u2a5.service;

import mx.edu.utez.u2a5.controller.dtos.RequestCreateJugadorDto;
import mx.edu.utez.u2a5.controller.dtos.RequestRecargarSaldoDto;
import mx.edu.utez.u2a5.controller.dtos.ResponseJugadorDto;
import mx.edu.utez.u2a5.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.u2a5.model.Jugador;
import mx.edu.utez.u2a5.repository.JugadorRepository;
import org.springframework.stereotype.Service;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    public ResponseJugadorDto crearJugador(RequestCreateJugadorDto payload) {
        Jugador jugador = new Jugador();
        jugador.setNombre(payload.getNombre());
        jugador.setSaldo(payload.getSaldo());
        jugador.setActivo(payload.getActivo());

        return convertirJugador(jugadorRepository.save(jugador));
    }

    public ResponseJugadorDto recargarSaldo(Long id, RequestRecargarSaldoDto payload) {
        Jugador jugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El jugador no existe."));

        jugador.setSaldo(jugador.getSaldo() + payload.getMonto());
        return convertirJugador(jugadorRepository.save(jugador));
    }

    private ResponseJugadorDto convertirJugador(Jugador jugador) {
        return new ResponseJugadorDto(
                jugador.getId(),
                jugador.getNombre(),
                jugador.getSaldo(),
                jugador.isActivo()
        );
    }
}
