package gurluk.goksel.VeterinaryProject.dto.request.Animal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalUpdateRequest {
    @Positive(message = "ID Değeri pozitif olmak zorunda")
    private long id;
    @NotNull(message = "Hayvan ismi boş veya null olamaz")
    private String name;
    private String species;
    private String breed;
    @Pattern(regexp = "erkek|dişi", message = "Gender must be either 'erkek' or 'dişi'")
    private String gender;
    private String colour;
    private LocalDate dateOfBirth;
    private long customerId;
}
