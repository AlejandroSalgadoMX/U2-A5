package mx.edu.utez.u2a5.controller;

import jakarta.validation.Valid;
import mx.edu.utez.u2a5.controller.dtos.RequestCreateJugadorDto;
import mx.edu.utez.u2a5.controller.dtos.RequestRecargarSaldoDto;
import mx.edu.utez.u2a5.controller.dtos.ResponseJugadorDto;
import mx.edu.utez.u2a5.service.JugadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/jugador")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @PostMapping
    public ResponseEntity<ResponseJugadorDto> crearJugador(
            @RequestBody @Valid RequestCreateJugadorDto payload
    ) {
        return ResponseEntity
                .status(201)
                .body(jugadorService.crearJugador(payload));
    }

    @PatchMapping("/{id}/recargar")
    public ResponseEntity<ResponseJugadorDto> recargarSaldo(
            @PathVariable Long id,
            @RequestBody @Valid RequestRecargarSaldoDto payload
    ) {
        return ResponseEntity
                .status(200)
                .body(jugadorService.recargarSaldo(id, payload));
    }
}
