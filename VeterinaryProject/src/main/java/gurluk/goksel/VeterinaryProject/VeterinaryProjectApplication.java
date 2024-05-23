package gurluk.goksel.VeterinaryProject;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Vet App",version = "1.0",description = "Veteriner Projesi"))

public class VeterinaryProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeterinaryProjectApplication.class, args);
	}

}