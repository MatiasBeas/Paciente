package cl.example.Paciente.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Api de Pacientes")
                .version("1.1.0")
                .description("Documentacion para Microservicio Paciente")
                .contact(new Contact()
                        .name("Equipo tecnico")
                        .email("comunicaciones.hgf@redsalud.gob.cl")
                        .url("https://www.hospitalfricke.cl/"))
                .license(new License()
                        .name("Hospital Viña del Mar")
                        .url("https://www.hospitalfricke.cl/pacientes")));
    }
}