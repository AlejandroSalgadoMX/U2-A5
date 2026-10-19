package mx.edu.utez.u2a5.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseJugadorDto {
    private Long id;
    private String nombre;
    private double saldo;
    private boolean activo;
}
