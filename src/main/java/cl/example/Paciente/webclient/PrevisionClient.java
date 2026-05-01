package cl.example.Paciente.webclient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PrevisionClient {
    private final WebClient webClient;

    public String obtenerNombrePrevision(Long idPrevision) {
        try {
            Map response = webClient
                    .get()
                    .uri("/previsiones/" + idPrevision)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            return response != null ? (String) response.get("nombre") : "Sin prevision";
        } catch (Exception e) {
            return "Sin prevision";
        }
    }
}
