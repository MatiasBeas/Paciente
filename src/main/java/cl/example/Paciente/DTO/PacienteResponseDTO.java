package cl.example.Paciente.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos retornados de un paciente")
public class PacienteResponseDTO {
    @Schema(description = "RUN del paciente", example = "22.359.190-6")
    private String run;

    @Schema(description = "Primer nombre del paciente", example = "Matias")
    private String pNombre;

    @Schema(description = "Segundo nombre del paciente", example = "Adolfo")
    private String sNombre;

    @Schema(description = "Primer apellido del paciente", example = "Beas")
    private String pApellido;

    @Schema(description = "Segundo apellido del paciente", example = "Beas")
    private String sApellido;

    @Schema(description = "Fecha de nacimiento del paciente", example = "2007-03-23")
    private Date fechaNacimiento;

    @Schema(description = "Nombre de la prevision del paciente", example = "Fonasa")
    private String nombrePrevision;
}
