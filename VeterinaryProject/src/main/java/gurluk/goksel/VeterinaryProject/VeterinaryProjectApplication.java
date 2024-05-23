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


	// 6 Katmanlı mimari kullanılmış mı? Entity, repository (dao), service (business), controller (api) katmanları oluşturulmuş mu? (4 Puan)
	// cevap >> gurluk.goksel.VeterinaryProject paketi
// 7 UML diyagramı doğru şekilde oluşturulmuş mu? (4 Puan)
	// cevap >> uml.png
// 8 Entity’ler doğru bir şekilde tanımlanmış mı? (4 Puan)
	// cevap >> entity sınıfı
// 25 Exception kullanılmış mı? (id ile güncelleme, silme işlemlerinde girilen id’de veri yoksa hata fırlatma gibi) (4 Puan)
	// cevap >> package gurluk.goksel.VeterinaryProject.core.config
// 26 HTTP durum kodları uygun şekilde kullanılmış mı? (4 Puan)
	// cevap >> package gurluk.goksel.VeterinaryProject.core.config.utiles
// 27 API endpoint'leri doğru şekilde dokümante edilmiş mi? (4 Puan)
	// cevap >> readme dosyası
}
