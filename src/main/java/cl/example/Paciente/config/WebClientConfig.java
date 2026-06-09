package cl.example.Paciente.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${webclient.prevision.url}")
    private String previsionUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(previsionUrl)
                .build();
    }
}