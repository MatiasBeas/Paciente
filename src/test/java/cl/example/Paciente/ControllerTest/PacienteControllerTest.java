package cl.example.Paciente.ControllerTest;

import cl.example.Paciente.Assemblers.PacienteAssembler;
import cl.example.Paciente.DTO.PacienteRequestDTO;
import cl.example.Paciente.DTO.PacienteResponseDTO;
import cl.example.Paciente.controller.PacienteController;
import cl.example.Paciente.security.JwtService;
import cl.example.Paciente.service.PacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PacienteController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(PacienteAssembler.class)
@DisplayName("Tests unitarios - PacienteController")
class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PacienteService pacienteService;

    @MockitoBean
    private PacienteAssembler pacienteAssembler;

    @MockitoBean
    private JwtService jwtService;

    private PacienteResponseDTO buildResponse() {
        return new PacienteResponseDTO(
                "22.359.190-6", "Juanito", "Pepe",
                "Perez", "Lobezno", new Date(), "Fonasa"
        );
    }

    private PacienteRequestDTO buildRequest() {
        return new PacienteRequestDTO(
                "22.359.190-6", "Juanito", "Pepe",
                "Perez", "Lobezno", new Date(), 1L
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GIVEN: pacientes en BD WHEN: GET /pacientes THEN: 200")
    void shouldReturn200_whenGetAll() throws Exception {
        PacienteResponseDTO dto = buildResponse();
        when(pacienteService.obtenerTodos()).thenReturn(List.of(dto));
        when(pacienteAssembler.toModel(any())).thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/pacientes"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GIVEN: RUN existente WHEN: GET /pacientes/{run} THEN: 200")
    void shouldReturn200_whenGetByRun() throws Exception {
        PacienteResponseDTO dto = buildResponse();
        when(pacienteService.obtenerPorRun("22.359.190-6")).thenReturn(Optional.of(dto));
        when(pacienteAssembler.toModel(any())).thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/pacientes/22.359.190-6"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GIVEN: RUN inexistente WHEN: GET /pacientes/{run} THEN: 404")
    void shouldReturn404_whenRunNotFound() throws Exception {
        when(pacienteService.obtenerPorRun("00.000.000-0")).thenReturn(Optional.empty());

        mockMvc.perform(get("/pacientes/00.000.000-0"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GIVEN: ID prevision existente WHEN: GET /pacientes/prevision/{id} THEN: 200")
    void shouldReturn200_whenGetByPrevision() throws Exception {
        when(pacienteService.obtenerPorPrevision(1L)).thenReturn(List.of(buildResponse()));

        mockMvc.perform(get("/pacientes/prevision/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GIVEN: DTO valido WHEN: POST /pacientes THEN: 201")
    void shouldReturn201_whenCreate() throws Exception {
        PacienteResponseDTO response = buildResponse();
        when(pacienteService.guardar(any())).thenReturn(response);
        when(pacienteAssembler.toModel(any())).thenReturn(EntityModel.of(response));

        mockMvc.perform(post("/pacientes")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildRequest())))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GIVEN: RUN existente WHEN: PUT /pacientes/{run} THEN: 200")
    void shouldReturn200_whenUpdate() throws Exception {
        PacienteResponseDTO response = buildResponse();
        when(pacienteService.actualizar(eq("22.359.190-6"), any())).thenReturn(Optional.of(response));
        when(pacienteAssembler.toModel(any())).thenReturn(EntityModel.of(response));

        mockMvc.perform(put("/pacientes/22.359.190-6")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildRequest())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GIVEN: RUN inexistente WHEN: PUT /pacientes/{run} THEN: 404")
    void shouldReturn404_whenUpdateNotFound() throws Exception {
        when(pacienteService.actualizar(eq("00.000.000-0"), any())).thenReturn(Optional.empty());

        mockMvc.perform(put("/pacientes/00.000.000-0")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildRequest())))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GIVEN: RUN existente WHEN: DELETE /pacientes/{run} THEN: 204")
    void shouldReturn204_whenDelete() throws Exception {
        when(pacienteService.obtenerPorRun("22.359.190-6")).thenReturn(Optional.of(buildResponse()));

        mockMvc.perform(delete("/pacientes/22.359.190-6")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GIVEN: RUN inexistente WHEN: DELETE /pacientes/{run} THEN: 404")
    void shouldReturn404_whenDeleteNotFound() throws Exception {
        when(pacienteService.obtenerPorRun("00.000.000-0")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/pacientes/00.000.000-0")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }
}