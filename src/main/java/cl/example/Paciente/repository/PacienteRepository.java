package cl.example.Paciente.repository;

import cl.example.Paciente.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, String> {
    List<Paciente> findByIdPrevision(Long idPrevision);
}
