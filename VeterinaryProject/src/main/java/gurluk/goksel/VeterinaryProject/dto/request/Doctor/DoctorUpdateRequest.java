package gurluk.goksel.VeterinaryProject.dto.request.Doctor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUpdateRequest {
    @Positive(message = "ID Değeri pozitif olmak zorunda")
    private long id;
    @NotNull(message = "Doktor ismi boş veya null olamaz")
    private String name;
    private Long phone;
    @Email(message = "Geçersiz e-posta adresi formatı")
    private String mail;
    private String address;
    private String city;
}
