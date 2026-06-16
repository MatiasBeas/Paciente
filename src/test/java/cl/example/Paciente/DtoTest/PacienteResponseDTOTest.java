package cl.example.Paciente.DtoTest;

import cl.example.Paciente.DTO.PacienteResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Tests unitarios - PacienteResponseDTO")
class PacienteResponseDTOTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private PacienteResponseDTO buildDto() {
        return new PacienteResponseDTO(
                "22.359.190-6", "Juanito", "Pepe",
                "Perez", "Lobezno", new Date(), "Fonasa"
        );
    }


    @Test
    @DisplayName("GIVEN: AllArgsConstructor WHEN: se construye THEN: todos los campos quedan asignados")
    void allArgsConstructor_shouldAssignAllFields() {
        Date fecha = new Date();
        PacienteResponseDTO dto = new PacienteResponseDTO(
                "22.359.190-6", "Juanito", "Pepe", "Perez", "Lobezno", fecha, "Fonasa"
        );

        assertThat(dto.getRun()).isEqualTo("22.359.190-6");
        assertThat(dto.getPNombre()).isEqualTo("Juanito");
        assertThat(dto.getSNombre()).isEqualTo("Pepe");
        assertThat(dto.getPApellido()).isEqualTo("Perez");
        assertThat(dto.getSApellido()).isEqualTo("Lobezno");
        assertThat(dto.getFechaNacimiento()).isEqualTo(fecha);
        assertThat(dto.getNombrePrevision()).isEqualTo("Fonasa");
    }

    @Test
    @DisplayName("GIVEN: NoArgsConstructor WHEN: se construye THEN: campos quedan nulos")
    void noArgsConstructor_shouldLeaveFieldsNull() {
        PacienteResponseDTO dto = new PacienteResponseDTO();

        assertThat(dto.getRun()).isNull();
        assertThat(dto.getPNombre()).isNull();
        assertThat(dto.getSNombre()).isNull();
        assertThat(dto.getPApellido()).isNull();
        assertThat(dto.getSApellido()).isNull();
        assertThat(dto.getFechaNacimiento()).isNull();
        assertThat(dto.getNombrePrevision()).isNull();
    }

    @Test
    @DisplayName("GIVEN: setters WHEN: se invocan THEN: actualizan el estado del objeto")
    void setters_shouldUpdateState() {
        PacienteResponseDTO dto = new PacienteResponseDTO();
        Date fecha = new Date();

        dto.setRun("11.111.111-1");
        dto.setPNombre("Carlos");
        dto.setSNombre("Andres");
        dto.setPApellido("Soto");
        dto.setSApellido("Diaz");
        dto.setFechaNacimiento(fecha);
        dto.setNombrePrevision("Isapre");

        assertThat(dto.getRun()).isEqualTo("11.111.111-1");
        assertThat(dto.getPNombre()).isEqualTo("Carlos");
        assertThat(dto.getSNombre()).isEqualTo("Andres");
        assertThat(dto.getPApellido()).isEqualTo("Soto");
        assertThat(dto.getSApellido()).isEqualTo("Diaz");
        assertThat(dto.getFechaNacimiento()).isEqualTo(fecha);
        assertThat(dto.getNombrePrevision()).isEqualTo("Isapre");
    }

    @Test
    @DisplayName("GIVEN: dos DTOs con los mismos valores WHEN: equals/hashCode THEN: son iguales")
    void equalsAndHashCode_shouldMatchForSameValues() {
        Date fecha = new Date();
        PacienteResponseDTO dto1 = new PacienteResponseDTO("22.359.190-6", "Juanito", "Pepe", "Perez", "Lobezno", fecha, "Fonasa");
        PacienteResponseDTO dto2 = new PacienteResponseDTO("22.359.190-6", "Juanito", "Pepe", "Perez", "Lobezno", fecha, "Fonasa");

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    @DisplayName("GIVEN: dos DTOs con distinta prevision WHEN: equals THEN: no son iguales")
    void equals_shouldDifferWhenFieldsDiffer() {
        Date fecha = new Date();
        PacienteResponseDTO dto1 = new PacienteResponseDTO("22.359.190-6", "Juanito", "Pepe", "Perez", "Lobezno", fecha, "Fonasa");
        PacienteResponseDTO dto2 = new PacienteResponseDTO("22.359.190-6", "Juanito", "Pepe", "Perez", "Lobezno", fecha, "Isapre");

        assertThat(dto1).isNotEqualTo(dto2);
    }

    @Test
    @DisplayName("GIVEN: un DTO WHEN: toString THEN: no lanza excepcion y contiene el run")
    void toString_shouldNotFailAndContainRun() {
        PacienteResponseDTO dto = buildDto();

        assertThat(dto.toString()).contains("22.359.190-6");
    }


    @Test
    @DisplayName("GIVEN: un DTO WHEN: se serializa a JSON THEN: usa los nombres reales que infiere Jackson (sin @JsonProperty)")
    void serialize_shouldContainAllFieldsWithDefaultNames() throws Exception {
        PacienteResponseDTO dto = buildDto();

        String json = objectMapper.writeValueAsString(dto);
        ObjectNode node = (ObjectNode) objectMapper.readTree(json);

        assertThat(node.has("run")).isTrue();
        assertThat(node.has("pnombre")).isTrue();
        assertThat(node.has("snombre")).isTrue();
        assertThat(node.has("papellido")).isTrue();
        assertThat(node.has("sapellido")).isTrue();
        assertThat(node.has("fechaNacimiento")).isTrue();
        assertThat(node.has("nombrePrevision")).isTrue();

        assertThat(node.has("pNombre")).isFalse();
        assertThat(node.has("sApellido")).isFalse();

        assertThat(node.get("run").asText()).isEqualTo("22.359.190-6");
        assertThat(node.get("pnombre").asText()).isEqualTo("Juanito");
        assertThat(node.get("sapellido").asText()).isEqualTo("Lobezno");
        assertThat(node.get("nombrePrevision").asText()).isEqualTo("Fonasa");
    }

    @Test
    @DisplayName("GIVEN: JSON con los nombres reales (minuscula) WHEN: se deserializa THEN: mapea correctamente los campos")
    void deserialize_shouldMapJsonToFields() throws Exception {
        String json = """
                {
                  "run": "22.359.190-6",
                  "pnombre": "Juanito",
                  "snombre": "Pepe",
                  "papellido": "Perez",
                  "sapellido": "Lobezno",
                  "fechaNacimiento": "2007-03-23T00:00:00.000+00:00",
                  "nombrePrevision": "Fonasa"
                }
                """;

        PacienteResponseDTO dto = objectMapper.readValue(json, PacienteResponseDTO.class);

        assertThat(dto.getRun()).isEqualTo("22.359.190-6");
        assertThat(dto.getPNombre()).isEqualTo("Juanito");
        assertThat(dto.getSNombre()).isEqualTo("Pepe");
        assertThat(dto.getPApellido()).isEqualTo("Perez");
        assertThat(dto.getSApellido()).isEqualTo("Lobezno");
        assertThat(dto.getNombrePrevision()).isEqualTo("Fonasa");
        assertThat(dto.getFechaNacimiento()).isNotNull();
    }

    @Test
    @DisplayName("GIVEN: JSON con nombres camelCase intuitivos (pNombre) WHEN: se deserializa THEN: falla por propiedad no reconocida")
    void deserialize_shouldFail_whenUsingIntuitiveCamelCaseNames() {
        String json = """
                {
                  "run": "22.359.190-6",
                  "pNombre": "Juanito"
                }
                """;

        assertThatThrownBy(() ->
                objectMapper.readValue(json, PacienteResponseDTO.class))
                .isInstanceOf(UnrecognizedPropertyException.class);
    }

    @Test
    @DisplayName("GIVEN: round-trip serializar y deserializar WHEN: se compara THEN: el objeto resultante es igual al original")
    void roundTrip_serializeThenDeserialize_shouldPreserveData() throws Exception {
        PacienteResponseDTO original = buildDto();

        String json = objectMapper.writeValueAsString(original);
        PacienteResponseDTO resultado = objectMapper.readValue(json, PacienteResponseDTO.class);

        assertThat(resultado).isEqualTo(original);
    }

    @Test
    @DisplayName("GIVEN: campos opcionales nulos (sNombre) WHEN: se serializa THEN: el campo aparece como null en el JSON")
    void serialize_shouldIncludeNullFieldsByDefault() throws Exception {
        PacienteResponseDTO dto = buildDto();
        dto.setSNombre(null);

        String json = objectMapper.writeValueAsString(dto);
        ObjectNode node = (ObjectNode) objectMapper.readTree(json);

        assertThat(node.has("snombre")).isTrue();
        assertThat(node.get("snombre").isNull()).isTrue();
    }
}