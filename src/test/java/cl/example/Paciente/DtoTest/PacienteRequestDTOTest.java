package cl.example.Paciente.DtoTest;

import cl.example.Paciente.DTO.PacienteRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests unitarios - PacienteRequestDTO")
class PacienteRequestDTOTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUpValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidator() {
        validatorFactory.close();
    }

    private PacienteRequestDTO buildValidDto() {
        return new PacienteRequestDTO(
                "22.359.190-6", "Juanito", "Pepe",
                "Perez", "Lobezno", new Date(), 1L
        );
    }

    @Test
    @DisplayName("GIVEN: AllArgsConstructor WHEN: se construye THEN: todos los campos quedan asignados")
    void allArgsConstructor_shouldAssignAllFields() {
        Date fecha = new Date();
        PacienteRequestDTO dto = new PacienteRequestDTO(
                "22.359.190-6", "Juanito", "Pepe", "Perez", "Lobezno", fecha, 1L
        );

        assertThat(dto.getRun()).isEqualTo("22.359.190-6");
        assertThat(dto.getPNombre()).isEqualTo("Juanito");
        assertThat(dto.getSNombre()).isEqualTo("Pepe");
        assertThat(dto.getPApellido()).isEqualTo("Perez");
        assertThat(dto.getSApellido()).isEqualTo("Lobezno");
        assertThat(dto.getFechaNacimiento()).isEqualTo(fecha);
        assertThat(dto.getIdPrevision()).isEqualTo(1L);
    }

    @Test
    @DisplayName("GIVEN: NoArgsConstructor WHEN: se construye THEN: campos quedan nulos")
    void noArgsConstructor_shouldLeaveFieldsNull() {
        PacienteRequestDTO dto = new PacienteRequestDTO();

        assertThat(dto.getRun()).isNull();
        assertThat(dto.getPNombre()).isNull();
        assertThat(dto.getSNombre()).isNull();
        assertThat(dto.getPApellido()).isNull();
        assertThat(dto.getSApellido()).isNull();
        assertThat(dto.getFechaNacimiento()).isNull();
        assertThat(dto.getIdPrevision()).isNull();
    }

    @Test
    @DisplayName("GIVEN: setters WHEN: se invocan THEN: actualizan el estado del objeto")
    void setters_shouldUpdateState() {
        PacienteRequestDTO dto = new PacienteRequestDTO();
        Date fecha = new Date();

        dto.setRun("11.111.111-1");
        dto.setPNombre("Carlos");
        dto.setSNombre("Andres");
        dto.setPApellido("Soto");
        dto.setSApellido("Diaz");
        dto.setFechaNacimiento(fecha);
        dto.setIdPrevision(2L);

        assertThat(dto.getRun()).isEqualTo("11.111.111-1");
        assertThat(dto.getPNombre()).isEqualTo("Carlos");
        assertThat(dto.getSNombre()).isEqualTo("Andres");
        assertThat(dto.getPApellido()).isEqualTo("Soto");
        assertThat(dto.getSApellido()).isEqualTo("Diaz");
        assertThat(dto.getFechaNacimiento()).isEqualTo(fecha);
        assertThat(dto.getIdPrevision()).isEqualTo(2L);
    }

    @Test
    @DisplayName("GIVEN: dos DTOs con los mismos valores WHEN: equals/hashCode THEN: son iguales")
    void equalsAndHashCode_shouldMatchForSameValues() {
        Date fecha = new Date();
        PacienteRequestDTO dto1 = new PacienteRequestDTO("22.359.190-6", "Juanito", "Pepe", "Perez", "Lobezno", fecha, 1L);
        PacienteRequestDTO dto2 = new PacienteRequestDTO("22.359.190-6", "Juanito", "Pepe", "Perez", "Lobezno", fecha, 1L);

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    @DisplayName("GIVEN: dos DTOs con distinto run WHEN: equals THEN: no son iguales")
    void equals_shouldDifferWhenFieldsDiffer() {
        Date fecha = new Date();
        PacienteRequestDTO dto1 = new PacienteRequestDTO("22.359.190-6", "Juanito", "Pepe", "Perez", "Lobezno", fecha, 1L);
        PacienteRequestDTO dto2 = new PacienteRequestDTO("11.111.111-1", "Juanito", "Pepe", "Perez", "Lobezno", fecha, 1L);

        assertThat(dto1).isNotEqualTo(dto2);
    }

    @Test
    @DisplayName("GIVEN: un DTO WHEN: toString THEN: no lanza excepcion y contiene el run")
    void toString_shouldNotFailAndContainRun() {
        PacienteRequestDTO dto = buildValidDto();

        assertThat(dto.toString()).contains("22.359.190-6");
    }

    @Test
    @DisplayName("GIVEN: un DTO WHEN: se serializa a JSON THEN: usa los nombres definidos en @JsonProperty")
    void serialize_shouldUseJsonPropertyNames() throws Exception {
        PacienteRequestDTO dto = buildValidDto();

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"run\"");
        assertThat(json).contains("\"pNombre\"");
        assertThat(json).contains("\"sNombre\"");
        assertThat(json).contains("\"pApellido\"");
        assertThat(json).contains("\"sApellido\"");
        assertThat(json).contains("\"fechaNacimiento\"");
        assertThat(json).contains("\"idPrevision\"");
    }

    @Test
    @DisplayName("GIVEN: JSON valido con nombres @JsonProperty WHEN: se deserializa THEN: mapea correctamente los campos")
    void deserialize_shouldMapJsonPropertyNamesToFields() throws Exception {
        String json = """
                {
                  "run": "22.359.190-6",
                  "pNombre": "Juanito",
                  "sNombre": "Pepe",
                  "pApellido": "Perez",
                  "sApellido": "Lobezno",
                  "fechaNacimiento": "2007-03-23T00:00:00.000+00:00",
                  "idPrevision": 1
                }
                """;

        PacienteRequestDTO dto = objectMapper.readValue(json, PacienteRequestDTO.class);

        assertThat(dto.getRun()).isEqualTo("22.359.190-6");
        assertThat(dto.getPNombre()).isEqualTo("Juanito");
        assertThat(dto.getSNombre()).isEqualTo("Pepe");
        assertThat(dto.getPApellido()).isEqualTo("Perez");
        assertThat(dto.getSApellido()).isEqualTo("Lobezno");
        assertThat(dto.getIdPrevision()).isEqualTo(1L);
        assertThat(dto.getFechaNacimiento()).isNotNull();
    }

    @Test
    @DisplayName("GIVEN: round-trip serializar y deserializar WHEN: se compara THEN: el objeto resultante es igual al original")
    void roundTrip_serializeThenDeserialize_shouldPreserveData() throws Exception {
        PacienteRequestDTO original = buildValidDto();

        String json = objectMapper.writeValueAsString(original);
        PacienteRequestDTO resultado = objectMapper.readValue(json, PacienteRequestDTO.class);

        assertThat(resultado).isEqualTo(original);
    }

    @Test
    @DisplayName("GIVEN: DTO completo y valido WHEN: se valida THEN: no hay violaciones")
    void validate_shouldHaveNoViolations_whenDtoIsValid() {
        PacienteRequestDTO dto = buildValidDto();

        Set<ConstraintViolation<PacienteRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("GIVEN: run en blanco WHEN: se valida THEN: se reporta violacion en run")
    void validate_shouldFail_whenRunIsBlank() {
        PacienteRequestDTO dto = buildValidDto();
        dto.setRun("   ");

        Set<ConstraintViolation<PacienteRequestDTO>> violations = validator.validate(dto);

        assertThat(violations)
                .anySatisfy(v -> {
                    assertThat(v.getPropertyPath().toString()).isEqualTo("run");
                    assertThat(v.getMessage()).isEqualTo("El Run no puede estar VACIO");
                });
    }

    @Test
    @DisplayName("GIVEN: pNombre nulo WHEN: se valida THEN: se reporta violacion en pNombre")
    void validate_shouldFail_whenPNombreIsNull() {
        PacienteRequestDTO dto = buildValidDto();
        dto.setPNombre(null);

        Set<ConstraintViolation<PacienteRequestDTO>> violations = validator.validate(dto);

        assertThat(violations)
                .anySatisfy(v -> assertThat(v.getPropertyPath().toString()).isEqualTo("pNombre"));
    }

    @Test
    @DisplayName("GIVEN: pApellido en blanco WHEN: se valida THEN: se reporta violacion en pApellido")
    void validate_shouldFail_whenPApellidoIsBlank() {
        PacienteRequestDTO dto = buildValidDto();
        dto.setPApellido("");

        Set<ConstraintViolation<PacienteRequestDTO>> violations = validator.validate(dto);

        assertThat(violations)
                .anySatisfy(v -> assertThat(v.getPropertyPath().toString()).isEqualTo("pApellido"));
    }

    @Test
    @DisplayName("GIVEN: sApellido en blanco WHEN: se valida THEN: se reporta violacion en sApellido")
    void validate_shouldFail_whenSApellidoIsBlank() {
        PacienteRequestDTO dto = buildValidDto();
        dto.setSApellido("");

        Set<ConstraintViolation<PacienteRequestDTO>> violations = validator.validate(dto);

        assertThat(violations)
                .anySatisfy(v -> assertThat(v.getPropertyPath().toString()).isEqualTo("sApellido"));
    }

    @Test
    @DisplayName("GIVEN: fechaNacimiento nula WHEN: se valida THEN: se reporta violacion en fechaNacimiento")
    void validate_shouldFail_whenFechaNacimientoIsNull() {
        PacienteRequestDTO dto = buildValidDto();
        dto.setFechaNacimiento(null);

        Set<ConstraintViolation<PacienteRequestDTO>> violations = validator.validate(dto);

        assertThat(violations)
                .anySatisfy(v -> assertThat(v.getPropertyPath().toString()).isEqualTo("fechaNacimiento"));
    }

    @Test
    @DisplayName("GIVEN: idPrevision nulo WHEN: se valida THEN: se reporta violacion en idPrevision")
    void validate_shouldFail_whenIdPrevisionIsNull() {
        PacienteRequestDTO dto = buildValidDto();
        dto.setIdPrevision(null);

        Set<ConstraintViolation<PacienteRequestDTO>> violations = validator.validate(dto);

        assertThat(violations)
                .anySatisfy(v -> assertThat(v.getPropertyPath().toString()).isEqualTo("idPrevision"));
    }

    @Test
    @DisplayName("GIVEN: sNombre nulo o vacio WHEN: se valida THEN: no se reporta violacion (campo opcional)")
    void validate_shouldNotFail_whenSNombreIsNullOrBlank() {
        PacienteRequestDTO dto = buildValidDto();
        dto.setSNombre(null);

        Set<ConstraintViolation<PacienteRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("GIVEN: todos los campos obligatorios vacios o nulos WHEN: se valida THEN: se acumulan todas las violaciones esperadas")
    void validate_shouldAccumulateAllViolations_whenAllRequiredFieldsAreInvalid() {
        PacienteRequestDTO dto = new PacienteRequestDTO(null, null, null, null, null, null, null);

        Set<ConstraintViolation<PacienteRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).hasSize(6);
    }
}