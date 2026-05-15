package cl.example.Paciente.service;

import cl.example.Paciente.DTO.PacienteRequestDTO;
import cl.example.Paciente.DTO.PacienteResponseDTO;
import cl.example.Paciente.model.Paciente;
import cl.example.Paciente.repository.PacienteRepository;
import cl.example.Paciente.webclient.PrevisionClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteService {
    private final PacienteRepository pacienteRepository;
    private final PrevisionClient previsionClient;

    //-----------------MAPEO PRIVADO: PACIENTE -> ResponseDTO----------
    private PacienteResponseDTO mapToDTO(Paciente paciente) {
        String nombrePrevision = previsionClient
                .obtenerNombrePrevision(paciente.getIdPrevision());
        return new PacienteResponseDTO(
                paciente.getRun(),
                paciente.getPNombre(),
                paciente.getSNombre(),
                paciente.getPApellido(),
                paciente.getSApellido(),
                paciente.getFechaNacimiento(),
                nombrePrevision
        );
    }


    //-----------------BUSCAR PACIENTE DE DISTINTAS FORMAS----------
    public List<PacienteResponseDTO> obtenerTodos() {
        log.info("Consultando TODOS los Pacientes");
        return pacienteRepository.findAll().stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }


    public Optional<PacienteResponseDTO> obtenerPorRun(String run) {
        log.info("Consultando el Paciente con el RUN:" + run);
        return pacienteRepository.findById(run).map(this::mapToDTO);

    }

    public List<PacienteResponseDTO> obtenerPorPrevision(Long idPrevision) {
        log.info("Consultando Pacientes con idPrevision: " + idPrevision);
        return pacienteRepository.findByIdPrevision(idPrevision).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    //-----------------CREACION PACIENTE----------
    public PacienteResponseDTO guardar(PacienteRequestDTO dto){
        log.info("Creando nuevo Paciente");
        Paciente paciente = new Paciente(
                dto.getRun(),
                dto.getPNombre(),
                dto.getSNombre(),
                dto.getPApellido(),
                dto.getSApellido(),
                dto.getFechaNacimiento(),
                dto.getIdPrevision()
        );
        return mapToDTO(pacienteRepository.save(paciente));
    }

    //-----------------ACTUALIZACION PACIENTE----------
    public Optional<PacienteResponseDTO> actualizar (String run, PacienteRequestDTO dto){
        log.info("Actualizando el Paciente con el RUN:" + run);
        return pacienteRepository.findById(run).map(existente ->{
            existente.setPNombre(dto.getPNombre());
            existente.setSNombre(dto.getSNombre());
            existente.setPApellido(dto.getPApellido());
            existente.setSApellido(dto.getSApellido());
            existente.setFechaNacimiento(dto.getFechaNacimiento());
            existente.setIdPrevision(dto.getIdPrevision());
            return mapToDTO(pacienteRepository.save(existente));
        });
    }

    //-----------------ELIMINAR PACIENTE----------
    public void eliminar(String run){
        pacienteRepository.deleteById(run);
        log.info("Eliminando el Paciente con el RUN:" + run);
    }
}