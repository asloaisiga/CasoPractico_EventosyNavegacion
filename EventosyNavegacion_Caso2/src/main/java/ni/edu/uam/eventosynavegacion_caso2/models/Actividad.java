package ni.edu.uam.eventosynavegacion_caso2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class Actividad {
    private String descripcion;
    private LocalDateTime fechaHora;

    public Actividad(String descripcion) {
        this.descripcion = descripcion;
        this.fechaHora = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return fechaHora.format(formato) + " - " + descripcion;
    }
}
