package cl.example.Paciente.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteResponseDTO {
    private String run;

    private String PNombre;
    private String SNombre;
    private String PApellido;
    private String SApellido;

    private Date fechaNacimiento;
    private Long idPrevision;
}
