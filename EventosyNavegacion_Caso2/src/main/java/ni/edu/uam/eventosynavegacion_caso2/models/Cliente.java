package ni.edu.uam.eventosynavegacion_caso2.models;

import java.time.LocalDate;
import java.util.List;

public class Cliente {
    private String nombres;
    private String apellidos;
    private String tipoCliente;
    private String ciudad;
    private LocalDate fechaNacimiento;
    private String tipoSolicitud;
    private List<String> serviciosInteres;
    private String rutaFotografia;

    public Cliente (String nombres, String apellidos, String tipoCliente,
                    String ciudad, LocalDate fechaNacimiento,
                    String tipoSolicitud, List<String> serviciosInteres,
                    String rutaFotografia) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoCliente = tipoCliente;
        this.ciudad = ciudad;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoSolicitud = tipoSolicitud;
        this.serviciosInteres = serviciosInteres;
        this.rutaFotografia = rutaFotografia;
    }


}