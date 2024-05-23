package gurluk.goksel.VeterinaryProject.business.abstracts;

import gurluk.goksel.VeterinaryProject.entity.Animal;
import gurluk.goksel.VeterinaryProject.entity.AvailableDate;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IAvailableDateService {
    AvailableDate save(AvailableDate availableDate);
    AvailableDate get(Long id);
    AvailableDate update (AvailableDate availableDate);
    Page<AvailableDate> cursor(int page, int pageSize);
    boolean delete(long id);
    boolean isDoctorAvailableOnDate(Long doctorId, LocalDate date);

    List<AvailableDate> getDatesByDoctorId(Long doctorId);
}