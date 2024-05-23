package gurluk.goksel.VeterinaryProject.dto.request.Doctor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSaveRequest {
    @NotNull
    private String name;
    private Long phone;
    @Email(message = "Geçersiz e-posta adresi formatı")
    private String mail;
    private String address;
    private String city;
}

