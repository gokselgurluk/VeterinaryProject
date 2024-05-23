package gurluk.goksel.VeterinaryProject.dto.request.Appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentSaveRequest {
    @Schema(example = "2024-05-22T11:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH")
    private LocalDateTime appointmentDateTime;
    private long doctorId;
   private long animalId;
}
