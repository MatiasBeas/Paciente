package cl.example.Paciente.controller;

import cl.example.Paciente.DTO.PacienteRequestDTO;
import cl.example.Paciente.DTO.PacienteResponseDTO;
import cl.example.Paciente.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {
    private final PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>>obtenerTodos(){
        return ResponseEntity.ok(pacienteService.obtenerTodos());
    }

    @GetMapping("/{run}")
    public ResponseEntity<PacienteResponseDTO> obtenerPorRun(@PathVariable String run){
        return pacienteService.obtenerPorRun(run).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> crearPaciente(@Valid @RequestBody PacienteRequestDTO dto){
        PacienteResponseDTO response = pacienteService.guardar(dto);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("{run}")
    public ResponseEntity<PacienteResponseDTO> actualizar(@PathVariable String run, @Valid @RequestBody PacienteRequestDTO dto){
        return pacienteService.actualizar(run,dto).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{run}")
    public ResponseEntity<Void> eliminar(@PathVariable String run){
        if (pacienteService.obtenerPorRun(run).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        pacienteService.eliminar(run);
        return ResponseEntity.noContent().build();
    }
}
