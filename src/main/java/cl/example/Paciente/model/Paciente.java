package cl.example.Paciente.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @Column(nullable = false)
    private String run;

    @Column(nullable = false, length = 100)
    private String pNombre;

    @Column(nullable = true, length = 100)
    private String sNombre;

    @Column(nullable = false, length = 100)
    private String pApellido;

    @Column(nullable = false, length = 100)
    private String sApellido;

    @Column(nullable = false)
    private Date fechaNacimiento;

    @Column(nullable = false)
    private Long idPrevision;
}
