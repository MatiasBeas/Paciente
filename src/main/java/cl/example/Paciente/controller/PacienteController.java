package cl.example.Paciente.controller;

import cl.example.Paciente.DTO.PacienteRequestDTO;
import cl.example.Paciente.DTO.PacienteResponseDTO;
import cl.example.Paciente.service.PacienteService;
import cl.example.Paciente.Assemblers.PacienteAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
@Tag(name = "Gestion de Pacientes", description = "Endpoints para administrar los pacientes del hospital")
public class PacienteController {
    private final PacienteService pacienteService;
    private final PacienteAssembler pacienteAssembler;

    //-----------------BUSCAR TODOS LOS PACIENTES----------
    @Operation(summary = "Obtener todos los pacientes", description = "Retorna una lista completa de todos los pacientes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pacientes obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PacienteResponseDTO>>> obtenerTodos() {
        List<EntityModel<PacienteResponseDTO>> pacientes = pacienteService.obtenerTodos()
                .stream()
                .map(pacienteAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(pacientes,
                linkTo(methodOn(PacienteController.class).obtenerTodos()).withSelfRel()));
    }

    //-----------------BUSCAR LOS PACIENTES POR EL RUN----------

    @Operation(summary = "Obtener paciente por RUN", description = "Retorna un paciente especifico por su RUN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @GetMapping("/{run}")
    public ResponseEntity<EntityModel<PacienteResponseDTO>> obtenerPorRun(
            @Parameter(description = "RUN del paciente", example = "22.359.190-6")
            @PathVariable String run) {
        return pacienteService.obtenerPorRun(run)
                .map(pacienteAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //-----------------BUSCAR LOS PACIENTES POR EL ID DE PREVISION----------
    @Operation(summary = "Obtener pacientes por prevision", description = "Retorna todos los pacientes de una prevision especifica")
    @GetMapping("/prevision/{idPrevision}")
    public ResponseEntity<List<PacienteResponseDTO>> obtenerPorPrevision(
            @Parameter(description = "ID de la prevision", example = "1")
            @PathVariable Long idPrevision) {
        return ResponseEntity.ok(pacienteService.obtenerPorPrevision(idPrevision));
    }

    //-----------------GUARDAR PACIENTE----------
    @Operation(summary = "Crear paciente", description = "Crea un nuevo paciente en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Paciente creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    @PostMapping
    public ResponseEntity<EntityModel<PacienteResponseDTO>> guardarPaciente(
             @RequestBody PacienteRequestDTO dto) {
        PacienteResponseDTO response = pacienteService.guardar(dto);
        return ResponseEntity.status(201).body(pacienteAssembler.toModel(response));
    }

    //-----------------ACTUALIZACION PACIENTE----------
    @Operation(summary = "Actualizar paciente", description = "Actualiza los datos de un paciente existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @PutMapping("/{run}")
    public ResponseEntity<EntityModel<PacienteResponseDTO>> actualizar(
            @Parameter(description = "RUN del paciente", example = "22.359.190-6")
            @PathVariable String run,
            @Valid @RequestBody PacienteRequestDTO dto) {
        return pacienteService.actualizar(run, dto)
                .map(pacienteAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //-----------------ELIMINAR PACIENTE----------
    @Operation(summary = "Eliminar paciente", description = "Elimina un paciente del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Paciente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado")
    })
    @DeleteMapping("/{run}")
    public ResponseEntity<Void> eliminarPaciente(
            @Parameter(description = "RUN del paciente", example = "22.359.190-6")
            @PathVariable String run) {
        if (pacienteService.obtenerPorRun(run).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        pacienteService.eliminar(run);
        return ResponseEntity.noContent().build();
    }
}
