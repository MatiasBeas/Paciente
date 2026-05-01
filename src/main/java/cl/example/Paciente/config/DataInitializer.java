package cl.example.Paciente.config;

import cl.example.Paciente.model.Paciente;
import cl.example.Paciente.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer  implements CommandLineRunner {
    private final PacienteRepository pacienteRepository;

    @Override
    public void run(String... args) throws Exception {

        if (pacienteRepository.count() > 0) {
            log.info(">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial.");
            return;
        }

        log.info(">>> DataInitializer: BD vacía detectada, insertando datos de prueba...");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        pacienteRepository.save(new Paciente("22.359.190-6", "Matias", "Adolfo", "Beas", "Beas", sdf.parse("2007-03-23"), 1L));
        pacienteRepository.save(new Paciente("18.765.432-1", "María", "José", "López", "Martínez", sdf.parse("1985-08-22"), 2L));
        pacienteRepository.save(new Paciente("11.111.111-1", "Carlos", null, "Ramírez", "Soto", sdf.parse("2000-01-10"), 1L));
        pacienteRepository.save(new Paciente("22.222.222-2", "Ana", "Lucía", "Torres", "Vega", sdf.parse("1978-11-30"), 3L));
        pacienteRepository.save(new Paciente("10.333.333-3", "Pedro", null, "Muñoz", "Rojas", sdf.parse("1995-03-08"), 2L));

        log.info(">>> DataInitializer: {} pacientes insertados correctamente.",
                pacienteRepository.count());
    }

}
