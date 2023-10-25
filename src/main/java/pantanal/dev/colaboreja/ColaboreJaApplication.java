package pantanal.dev.colaboreja;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ColaboreJaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ColaboreJaApplication.class, args);
	}

	@PostConstruct
	public void init(){
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
	}

}
