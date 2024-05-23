package gurluk.goksel.VeterinaryProject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDataResponse {
    private long Id;
    private LocalDate availableDate;
   // private long doctorId;
}
