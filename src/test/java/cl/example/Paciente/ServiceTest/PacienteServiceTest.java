package cl.example.Paciente;

import cl.example.Paciente.DTO.PacienteRequestDTO;
import cl.example.Paciente.DTO.PacienteResponseDTO;
import cl.example.Paciente.model.Paciente;
import cl.example.Paciente.repository.PacienteRepository;
import cl.example.Paciente.service.PacienteService;
import cl.example.Paciente.webclient.PrevisionClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - PacienteService")
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private PrevisionClient previsionClient;

    @InjectMocks
    private PacienteService pacienteService;

    @Test
    @DisplayName("GIVEN: pacientes en BD WHEN: obtenerTodos THEN: retorna lista de DTOs")
    void shouldReturnAllPacientes() {
        Paciente p1 = new Paciente("11.111.111-1", "Juan", null, "Perez", "Lopez", new Date(), 1L);
        Paciente p2 = new Paciente("22.222.222-2", "Maria", null, "Soto", "Rojas", new Date(), 2L);
        when(pacienteRepository.findAll()).thenReturn(List.of(p1, p2));
        when(previsionClient.obtenerNombrePrevision(anyLong())).thenReturn("Fonasa");

        List<PacienteResponseDTO> resultado = pacienteService.obtenerTodos();

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getPNombre()).isEqualTo("Juan");
        assertThat(resultado.get(1).getPNombre()).isEqualTo("Maria");
    }

    @Test
    @DisplayName("GIVEN: RUN existente WHEN: obtenerPorRun THEN: retorna el DTO")
    void shouldReturnPacienteByRun() {
        Paciente p = new Paciente("12.345.678-9", "Pedro", null, "Munoz", "Diaz", new Date(), 1L);
        when(pacienteRepository.findById("12.345.678-9")).thenReturn(Optional.of(p));
        when(previsionClient.obtenerNombrePrevision(1L)).thenReturn("Fonasa");

        Optional<PacienteResponseDTO> resultado = pacienteService.obtenerPorRun("12.345.678-9");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getPNombre()).isEqualTo("Pedro");
    }

    @Test
    @DisplayName("GIVEN: RUN inexistente WHEN: obtenerPorRun THEN: retorna empty")
    void shouldReturnEmptyWhenRunNotFound() {
        when(pacienteRepository.findById("99.999.999-9")).thenReturn(Optional.empty());

        Optional<PacienteResponseDTO> resultado = pacienteService.obtenerPorRun("99.999.999-9");

        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("GIVEN: prevision existente WHEN: guardar THEN: guarda y retorna DTO")
    void shouldSavePacienteWhenPrevisionExists() {
        PacienteRequestDTO request = new PacienteRequestDTO(
                "33.333.333-3", "Sofia", "Isabel", "Vargas", "Mora", new Date(), 1L);
        Paciente guardado = new Paciente("33.333.333-3", "Sofia", "Isabel", "Vargas", "Mora", new Date(), 1L);

        when(previsionClient.existePrevision(1L)).thenReturn(true);
        when(previsionClient.obtenerNombrePrevision(1L)).thenReturn("Fonasa");
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(guardado);

        PacienteResponseDTO resultado = pacienteService.guardar(request);

        assertThat(resultado.getRun()).isEqualTo("33.333.333-3");
        assertThat(resultado.getPNombre()).isEqualTo("Sofia");
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    @DisplayName("GIVEN: prevision inexistente WHEN: guardar THEN: lanza RuntimeException")
    void shouldThrowExceptionWhenPrevisionNotExists() {
        PacienteRequestDTO request = new PacienteRequestDTO(
                "44.444.444-4", "Luis", null, "Torres", "Vega", new Date(), 99L);

        when(previsionClient.existePrevision(99L)).thenReturn(false);

        assertThatThrownBy(() -> pacienteService.guardar(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("GIVEN: RUN existente WHEN: eliminar THEN: llama deleteById una vez")
    void shouldDeletePaciente() {
        doNothing().when(pacienteRepository).deleteById("11.111.111-1");

        pacienteService.eliminar("11.111.111-1");

        verify(pacienteRepository, times(1)).deleteById("11.111.111-1");
    }
}