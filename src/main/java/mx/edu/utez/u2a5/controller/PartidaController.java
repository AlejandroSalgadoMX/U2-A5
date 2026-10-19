package mx.edu.utez.u2a5.controller;

import jakarta.validation.Valid;
import mx.edu.utez.u2a5.controller.dtos.RequestIniciarPartidaDto;
import mx.edu.utez.u2a5.controller.dtos.ResponsePartidaDto;
import mx.edu.utez.u2a5.controller.dtos.ResponseTiroDto;
import mx.edu.utez.u2a5.service.PartidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/partida")
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @PostMapping("/iniciar")
    public ResponseEntity<ResponsePartidaDto> iniciarPartida(
            @RequestBody @Valid RequestIniciarPartidaDto payload
    ) {
        return ResponseEntity
                .status(201)
                .body(partidaService.iniciarPartida(payload));
    }

    @PostMapping("/{id}/tiro")
    public ResponseEntity<ResponseTiroDto> realizarTiro(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(partidaService.realizarTiro(id));
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<ResponsePartidaDto> finalizarPartidaManual(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(partidaService.finalizarPartidaManual(id));
    }

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<ResponsePartidaDto>> obtenerHistorialPartida(@PathVariable Long jugadorId) {
        return ResponseEntity
                .status(200)
                .body(partidaService.obtenerHistorialPartida(jugadorId));
    }
}
