package cl.example.Paciente;

import cl.example.Paciente.model.Paciente;
import cl.example.Paciente.repository.PacienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Tests unitarios - PacienteRepository")
class PacienteRepositoryTest {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Test
    @DisplayName("GIVEN: un paciente nuevo WHEN: save THEN: se guarda correctamente")
    void shouldSavePaciente() {
        Paciente paciente = new Paciente("12.345.678-9", "Juan", "Carlos", "Perez", "Lopez", new Date(), 1L);

        Paciente guardado = pacienteRepository.save(paciente);

        assertThat(guardado.getRun()).isEqualTo("12.345.678-9");
        assertThat(guardado.getPNombre()).isEqualTo("Juan");
    }

    @Test
    @DisplayName("GIVEN: un paciente guardado WHEN: findById THEN: lo retorna")
    void shouldFindPacienteByRun() {
        Paciente paciente = new Paciente("22.333.444-5", "Maria", null, "Gonzalez", "Rojas", new Date(), 2L);
        pacienteRepository.save(paciente);

        Optional<Paciente> encontrado = pacienteRepository.findById("22.333.444-5");

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getPNombre()).isEqualTo("Maria");
    }

    @Test
    @DisplayName("GIVEN: varios pacientes guardados WHEN: findAll THEN: retorna todos")
    void shouldFindAllPacientes() {
        pacienteRepository.save(new Paciente("11.111.111-1", "Pedro", null, "Soto", "Silva", new Date(), 1L));
        pacienteRepository.save(new Paciente("22.222.222-2", "Ana", null, "Torres", "Vega", new Date(), 1L));

        List<Paciente> todos = pacienteRepository.findAll();

        assertThat(todos).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("GIVEN: pacientes con misma prevision WHEN: findByIdPrevision THEN: retorna solo los de esa prevision")
    void shouldFindPacientesByPrevision() {
        pacienteRepository.save(new Paciente("33.333.333-3", "Luis", null, "Munoz", "Diaz", new Date(), 1L));
        pacienteRepository.save(new Paciente("44.444.444-4", "Sofia", null, "Vargas", "Mora", new Date(), 2L));

        List<Paciente> resultado = pacienteRepository.findByIdPrevision(1L);

        assertThat(resultado).isNotEmpty();
        assertThat(resultado).allMatch(p -> p.getIdPrevision().equals(1L));
    }

    @Test
    @DisplayName("GIVEN: un paciente guardado WHEN: deleteById THEN: ya no se encuentra")
    void shouldDeletePaciente() {
        Paciente paciente = new Paciente("55.555.555-5", "Carlos", null, "Reyes", "Fuentes", new Date(), 1L);
        pacienteRepository.save(paciente);

        pacienteRepository.deleteById("55.555.555-5");

        assertThat(pacienteRepository.findById("55.555.555-5")).isEmpty();
    }
}