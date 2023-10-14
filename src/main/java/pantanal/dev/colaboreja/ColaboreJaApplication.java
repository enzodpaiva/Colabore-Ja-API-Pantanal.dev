package pantanal.dev.colaboreja;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import static pantanal.dev.colaboreja.auth.Role.ADMIN;
import pantanal.dev.colaboreja.model.RegisterRequest;
import pantanal.dev.colaboreja.service.AuthenticationService;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ColaboreJaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ColaboreJaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("admin@mail.com")
					.password("password")
					.role(ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());		

		};
	}
}
