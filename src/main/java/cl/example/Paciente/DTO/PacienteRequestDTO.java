package cl.example.Paciente.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRequestDTO {

    @NotBlank(message = "El Run no puede estar VACIO")
    private String run;

    @NotBlank(message = "El PRIMER NOMBRE no puede estar VACIO")
    private String PNombre;

    @NotBlank(message = "El SEGUNDO NOMBRE no puede estar VACIO")
    private String SNombre;

    @NotBlank(message = "El PRIMER APELLIDO no puede estar VACIO")
    private String PApellido;

    @NotBlank(message = "El SEGUNDO NOMBRE no puede estar VACIO")
    private String SApellido;

    @NotNull(message = "La FECHA es OBLIGATORIA")
    private Date fechaNacimiento;

    @NotNull(message = "El IDPrevision es OBLIGATORIO")
    private Long idPrevision;


}
