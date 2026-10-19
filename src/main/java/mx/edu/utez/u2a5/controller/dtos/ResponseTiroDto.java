package mx.edu.utez.u2a5.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.u2a5.model.EstadoPartida;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTiroDto {
    private Long id;
    private Long partidaId;
    private int valorDado1;
    private int valorDado2;
    private int suma;
    private boolean esGanador;
    private EstadoPartida estadoPartida;
}
