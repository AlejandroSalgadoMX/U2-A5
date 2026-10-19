package mx.edu.utez.u2a5.controller.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestIniciarPartidaDto {

    @NotNull(message = "El jugador es obligatorio")
    @Min(value = 1, message = "El id del jugador debe ser válido")
    private Long jugadorId;

    @NotNull(message = "La apuesta es obligatoria")
    @DecimalMin(value = "0.01", message = "La apuesta debe ser mayor a cero")
    private Double apuesta;
}
