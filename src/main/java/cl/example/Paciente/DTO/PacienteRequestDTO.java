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
    private String pNombre;

    private String sNombre;

    @NotBlank(message = "El PRIMER APELLIDO no puede estar VACIO")
    private String pApellido;

    @NotBlank(message = "El SEGUNDO NOMBRE no puede estar VACIO")
    private String sApellido;

    @NotNull(message = "La FECHA es OBLIGATORIA")
    private Date fechaNacimiento;

    @NotNull(message = "El IDPrevision es OBLIGATORIO")
    private Long idPrevision;


}
