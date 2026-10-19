package mx.edu.utez.u2a5.service;

import mx.edu.utez.u2a5.controller.dtos.RequestIniciarPartidaDto;
import mx.edu.utez.u2a5.controller.dtos.ResponsePartidaDto;
import mx.edu.utez.u2a5.controller.dtos.ResponseTiroDto;
import mx.edu.utez.u2a5.exception.customExceptions.CustomBadRequestException;
import mx.edu.utez.u2a5.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.u2a5.model.EstadoPartida;
import mx.edu.utez.u2a5.model.Jugador;
import mx.edu.utez.u2a5.model.Partida;
import mx.edu.utez.u2a5.model.Tiro;
import mx.edu.utez.u2a5.repository.JugadorRepository;
import mx.edu.utez.u2a5.repository.PartidaRepository;
import mx.edu.utez.u2a5.repository.TiroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class PartidaService {

    private final JugadorRepository jugadorRepository;
    private final PartidaRepository partidaRepository;
    private final TiroRepository tiroRepository;
    private final Random random = new Random();

    public PartidaService(
            JugadorRepository jugadorRepository,
            PartidaRepository partidaRepository,
            TiroRepository tiroRepository
    ) {
        this.jugadorRepository = jugadorRepository;
        this.partidaRepository = partidaRepository;
        this.tiroRepository = tiroRepository;
    }

    @Transactional
    public ResponsePartidaDto iniciarPartida(RequestIniciarPartidaDto payload) {
        Jugador jugador = jugadorRepository.findById(payload.getJugadorId())
                .orElseThrow(() -> new CustomNotFoundException("El jugador no existe."));

        if (!jugador.isActivo()) {
            throw new CustomBadRequestException("El jugador no está activo.");
        }
        if (jugador.getSaldo() < payload.getApuesta()) {
            throw new CustomBadRequestException("El jugador no tiene saldo suficiente.");
        }

        jugador.setSaldo(jugador.getSaldo() - payload.getApuesta());
        jugadorRepository.save(jugador);

        Partida partida = new Partida();
        partida.setFecha(LocalDateTime.now());
        partida.setEstado(EstadoPartida.EN_JUEGO);
        partida.setJugador(jugador);
        partida.setApuesta(payload.getApuesta());

        return convertirPartida(partidaRepository.save(partida));
    }

    @Transactional
    public ResponseTiroDto realizarTiro(Long id) {
        Partida partida = obtenerPartida(id);

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new CustomBadRequestException("La partida no está en juego.");
        }

        int valorDado1 = random.nextInt(6) + 1;
        int valorDado2 = random.nextInt(6) + 1;
        int suma = valorDado1 + valorDado2;
        boolean esGanador = suma == 7 || suma == 11;
        boolean esPerdedor = suma == 2 || suma == 3 || suma == 12;

        if (esGanador) {
            partida.setEstado(EstadoPartida.FINALIZADA);
            Jugador jugador = partida.getJugador();
            jugador.setSaldo(jugador.getSaldo() + (partida.getApuesta() * 2));
            jugadorRepository.save(jugador);
        }

        if (esPerdedor) {
            partida.setEstado(EstadoPartida.FINALIZADA);
        }

        partidaRepository.save(partida);

        Tiro tiro = new Tiro();
        tiro.setPartida(partida);
        tiro.setValorDado1(valorDado1);
        tiro.setValorDado2(valorDado2);
        tiro.setSuma(suma);
        tiro.setEsGanador(esGanador);

        tiro = tiroRepository.save(tiro);

        return new ResponseTiroDto(
                tiro.getId(),
                partida.getId(),
                tiro.getValorDado1(),
                tiro.getValorDado2(),
                tiro.getSuma(),
                tiro.isEsGanador(),
                partida.getEstado()
        );
    }

    public ResponsePartidaDto finalizarPartidaManual(Long id) {
        Partida partida = obtenerPartida(id);

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new CustomBadRequestException("Solo se puede finalizar una partida que está en juego.");
        }

        partida.setEstado(EstadoPartida.FINALIZADA);
        return convertirPartida(partidaRepository.save(partida));
    }

    public List<ResponsePartidaDto> obtenerHistorialPartida(Long jugadorId) {
        if (!jugadorRepository.existsById(jugadorId)) {
            throw new CustomNotFoundException("El jugador no existe.");
        }

        return partidaRepository.findByJugadorIdOrderByFechaDesc(jugadorId)
                .stream()
                .map(this::convertirPartida)
                .toList();
    }

    private Partida obtenerPartida(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("La partida no existe."));
    }

    private ResponsePartidaDto convertirPartida(Partida partida) {
        return new ResponsePartidaDto(
                partida.getId(),
                partida.getFecha(),
                partida.getEstado(),
                partida.getJugador().getId(),
                partida.getApuesta()
        );
    }
}
