package cl.example.Paciente.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos requeridos para crear o actualizar un paciente")
public class PacienteRequestDTO {

    @NotBlank(message = "El Run no puede estar VACIO")
    @Schema(description = "RUN del paciente", example = "22.359.190-6")
    private String run;

    @NotBlank(message = "El PRIMER NOMBRE no puede estar VACIO")
    @Schema(description = "Primer nombre del paciente", example = "Juanito")
    @JsonProperty("pNombre")
    private String pNombre;

    @Schema(description = "Segundo nombre del paciente", example = "Pepe")
    @JsonProperty("sNombre")
    private String sNombre;

    @NotBlank(message = "El PRIMER APELLIDO no puede estar VACIO")
    @Schema(description = "Primer apellido del paciente", example = "Perez")
    @JsonProperty("pApellido")
    private String pApellido;

    @NotBlank(message = "El SEGUNDO NOMBRE no puede estar VACIO")
    @Schema(description = "Segundo apellido del paciente", example = "Lobezno")
    @JsonProperty("sApellido")
    private String sApellido;

    @NotNull(message = "La FECHA es OBLIGATORIA")
    @Schema(description = "Fecha de nacimiento del paciente", example = "2007-03-23")
    private Date fechaNacimiento;

    @NotNull(message = "El IDPrevision es OBLIGATORIO")
    @Schema(description = "ID de la prevision del paciente", example = "1")
    private Long idPrevision;

}
