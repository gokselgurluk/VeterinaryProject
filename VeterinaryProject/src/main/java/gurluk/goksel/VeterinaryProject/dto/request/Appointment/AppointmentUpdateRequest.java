package gurluk.goksel.VeterinaryProject.dto.request.Appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest {
  /*  @Positive(message = "ID Değeri pozitif olmak zorunda")
    private long id;*/
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH")
    private LocalDateTime appointmentDateTime;
   /* private long doctorId;
    private long animalId;*/
}
