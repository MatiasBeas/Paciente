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
    @Column(nullable = false, unique = true)
    private String run;

    @Column(nullable = false, length = 100)
    private String PNombre;

    @Column(nullable = true, length = 100)
    private String SNombre;

    @Column(nullable = false, length = 100)
    private String PApellido;

    @Column(nullable = false, length = 100)
    private String SApellido;

    @Column(nullable = false)
    private Date fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "previsionId", nullable = false)
    private Long idPrevision;
}
