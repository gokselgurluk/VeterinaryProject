package gurluk.goksel.VeterinaryProject.business.concretes;

import gurluk.goksel.VeterinaryProject.business.abstracts.IAppointmentService;
import gurluk.goksel.VeterinaryProject.core.config.exeption.NotFoundException;
import gurluk.goksel.VeterinaryProject.core.config.utiles.Msg;
import gurluk.goksel.VeterinaryProject.dao.AppointmentRepository;
import gurluk.goksel.VeterinaryProject.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService implements IAppointmentService {
    private final AppointmentRepository appointmentRepo;

    public AppointmentService(AppointmentRepository appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepo.save(appointment);
    }


    @Override
    public Appointment get(Long id) {
        return this.appointmentRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }


    @Override
    public Page<Appointment> cursor(int page, int pageSize) {
        // Sayfalama için PageRequest kullanılır
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.appointmentRepo.findAll(pageable);// findAll metodu kullanılır
    }


    @Override
    public Appointment update(Appointment appointment) {
        this.get(appointment.getId());
        return this.appointmentRepo.save(appointment);
    }

    @Override
    public boolean delete(long id) {
        Appointment appointment = this.get(id);
        this.appointmentRepo.delete(appointment);
        return true;
    }

    @Override
    public List<Appointment> getByDoctorId(Long doctorId) {
        if (doctorId == null || doctorId <= 0){
            throw new IllegalArgumentException(Msg.NOT_FOUND);
        }
        return this.appointmentRepo.findByDoctorId(doctorId);

    }

    @Override
    public List<Appointment> getByAnimalId(Long animalId) {
        if (animalId == null || animalId <= 0){
            throw new IllegalArgumentException(Msg.NOT_FOUND);
        }
        return this.appointmentRepo.findByAnimalId(animalId);
    }

    @Override

    public List<Appointment> getAppointmentsByDateRange(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        return appointmentRepo.findByAppointmentDateTimeBetween(startDateTime, endDateTime);
    }
    // Belirli bir tarih aralığındaki tüm randevuları getirir
  /*  @Override
    public List<Appointment> getAppointmentsByDateRange(String startDate, String endDate) {
        LocalDateTime startDateTime = LocalDateTime.parse(startDate);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate);
        List<Appointment> appointments = appointmentRepo.findByAppointmentDateTimeBetween(startDateTime, endDateTime);
        return appointments;
    }*/

    @Override
    public boolean isAppointmentConflict(Long doctorId, LocalDateTime dateTime) {
        List<Appointment> conflictingAppointments = appointmentRepo.findByDoctorIdAndAppointmentDateTime(doctorId, dateTime);
        return !conflictingAppointments.isEmpty();
    }

    @Override
    public List<Appointment> getAppointmentsByDateRangeAndDoctorId(LocalDate startDate, LocalDate endDate, Long doctorId) {
        // Doktor ID'sine göre ve tarih aralığına göre randevuları al
        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIDNIGHT);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59));
        return appointmentRepo.findByDateRangeAndDoctorId(startDateTime, endDateTime, doctorId);
    }
    @Override
    public List<Appointment> getAppointmentsByDateRangeAndAnimalId(LocalDate startDate, LocalDate endDate, Long animalId) {
        // Doktor ID'sine göre ve tarih aralığına göre randevuları al
        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIDNIGHT);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59));
        return appointmentRepo.findByDateRangeAndAnimalId(startDateTime, endDateTime, animalId);
    }
}