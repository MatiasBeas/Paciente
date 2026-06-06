package cl.example.Paciente.Assemblers;

import cl.example.Paciente.DTO.PacienteResponseDTO;
import cl.example.Paciente.controller.PacienteController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PacienteAssembler implements RepresentationModelAssembler<PacienteResponseDTO, EntityModel<PacienteResponseDTO>> {

    @Override
    public EntityModel<PacienteResponseDTO> toModel(PacienteResponseDTO paciente) {
        return EntityModel.of(paciente,
                linkTo(methodOn(PacienteController.class)
                        .obtenerPorRun(paciente.getRun())).withSelfRel(),
                linkTo(methodOn(PacienteController.class)
                        .obtenerTodos()).withRel("todos-los-pacientes"));
    }
}
