package gurluk.goksel.VeterinaryProject.dto.request.Customer;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class CustomerUpdateRequest {
    @Positive(message = "ID Değeri pozitif olmak zorunda")
    private long id;
    @NotNull(message = "Müşteri ismi boş veya null olamaz")
    private String name;
    private String phone;
    @Email(message = "Geçersiz e-posta adresi formatı")
    private String mail;
    private String address;
    private String city;


    }

