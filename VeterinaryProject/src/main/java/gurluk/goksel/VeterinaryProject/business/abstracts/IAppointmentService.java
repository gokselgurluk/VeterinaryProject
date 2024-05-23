package gurluk.goksel.VeterinaryProject.business.abstracts;


import gurluk.goksel.VeterinaryProject.entity.Appointment;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {
    Appointment save(Appointment appointment);
    Appointment get(Long id);
    Page<Appointment> cursor(int page, int pageSize);
    Appointment update (Appointment appointment);
    boolean delete (long id);

    List<Appointment> getByDoctorId(Long doctorId);
    List<Appointment> getByAnimalId(Long animalId);

    //List<Appointment> getAppointmentsByDateRange(String startDate, String endDate);
    List<Appointment> getAppointmentsByDateRange(LocalDate startDate, LocalDate endDate);
    boolean isAppointmentConflict(Long doctorId, LocalDateTime dateTime);

    List<Appointment> getAppointmentsByDateRangeAndDoctorId(LocalDate startDate, LocalDate endDate, Long doctorId);
    List<Appointment> getAppointmentsByDateRangeAndAnimalId(LocalDate startDate, LocalDate endDate, Long animalId);
}
